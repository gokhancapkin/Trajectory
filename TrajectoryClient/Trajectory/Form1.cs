using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using GMap.NET;
using GMap.NET.MapProviders;
using GMap.NET.WindowsForms;
using System.IO;
using Newtonsoft.Json;
using System.Net.Sockets;


namespace Trajectory
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }
        static List<PointLatLng> points = new List<PointLatLng>();

        private void BtnGonder_Click(object sender, EventArgs e)
        {
            map.DragButton = MouseButtons.Left;
            map.MapProvider = GMapProviders.GoogleMap;
            AddTrajectoryData();
            double lat1 = points[0].Lat;
            double long1 = points[0].Lng;
            map.Position = new PointLatLng(lat1, long1);
            map.MinZoom = 5;
            map.MaxZoom = 100;
            map.Zoom = 10;
            GMapOverlay routes = new GMapOverlay("routes");
            GMapRoute route = new GMapRoute(points, "Gezinge Yolu");
            route.Stroke = new Pen(Color.Red, 3);
            routes.Routes.Add(route);
            map.Overlays.Add(routes);
            map.Zoom = 11;
        }
        private void AddTrajectoryData()
        {
            OpenFileDialog file = new OpenFileDialog();
            file.Filter = "Plt |*.plt| Txt |*.txt";
            file.Title = "Verileri Yükle";
            file.RestoreDirectory = true;

            if (file.ShowDialog() != System.Windows.Forms.DialogResult.OK)
            {
                return;
            }
            string gezingePlt = file.FileName; 
            FileStream gezingeFS = new FileStream(gezingePlt, FileMode.Open, FileAccess.Read);
            StreamReader gezingeSR = new StreamReader(gezingeFS);
            string satir = gezingeSR.ReadLine();
            int satirSay = 1;
            int sutun = 0;
            double lat = 0, lng = 0;
            Console.WriteLine("Okuma Başlıyor..");
            while (satir != null)
            {
                sutun = 0;
                string[] words = satir.Split(',');
                foreach (string word in words)
                {
                    if (satirSay <7) break;
                    if (sutun == 0)
                    {
                       lat = Convert.ToDouble(word.Replace(".", ","));
                        sutun++;
                    }
                    else if (sutun == 1)
                    {
                        lng = Convert.ToDouble(word.Replace(".", ","));
                        sutun++;
                        break;
                    }
                    else
                    {
                        break;
                    }


                }
                satirSay++;
                if (satirSay > 7)
                {

                    points.Add(new PointLatLng(lat, lng));
                }
                satir = gezingeSR.ReadLine();
            }
            Console.WriteLine("Okuma Bitti");
        }

        static TcpClient request = new TcpClient();
        private void BtnIndirge_Click(object sender, EventArgs e)
        {
            request.Connect("192.168.1.21", 5555);
            /*string json = JsonConvert.SerializeObject(points);
            Connect(json);*/
            
            for (int i = 0; i < points.Count; i++)
            {
                Connect(points[i].Lat+"-"+points[i].Lng+"/");
                Console.WriteLine(points[i].Lat + "-" + points[i].Lng + "/");
            }
            
        }

        static void Connect(String message)
        {

            byte[] writeByte = Encoding.UTF8.GetBytes(message);
            NetworkStream stream = request.GetStream();
            stream.Write(writeByte, 0, writeByte.Length);
            byte[] readByte = new Byte[request.ReceiveBufferSize];
            var receiveFromServer = Encoding.UTF8.GetString(readByte);
            Console.WriteLine("Server : " + receiveFromServer.Replace("\0", null));

        }

        public class JsonLatLng
        {
            public Latlongveri LatlongVeri { get; set; }
        }

        public class Latlongveri
        { 
            public int lat { get; set; }
            public int lng { get; set; }
        }

        

        
    }
}
