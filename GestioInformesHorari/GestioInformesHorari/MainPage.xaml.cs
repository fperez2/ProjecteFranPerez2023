using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Navigation;
using GestioInformesHorariBD;
using GestioInformesHorariClasses;
using GestioInformesHorari.View;

namespace GestioInformesHorari
{
    public sealed partial class MainPage : Page
    {

        private EPGestioInformesHorari epGestio = new EPGestioInformesHorari();
        public MainPage()
        {
            this.InitializeComponent();
        }

        private void btnLogin_Click(object sender, RoutedEventArgs e)
        {
            string login = txbLogin.Text;
            string password = txbPassword.Password;
            
            string res = epGestio.Login(login, password);
            if (res != null)
            {
                Frame.Navigate(typeof(GestorInformes), res);
            }
            else
            {
                txbError.Text = "Dades incorrectes.";
            }
        }
    }
}
