using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using System.Threading.Tasks;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Navigation;

namespace GestioInformesHorari.View.MyViewModel
{
    public sealed partial class BarraNavegacio : Page
    {
        int codiMetge;
        public BarraNavegacio()
        {
            this.InitializeComponent();
        }
        protected override void OnNavigatedTo(NavigationEventArgs e)
        {
            if (e.Parameter != null)
            {
                codiMetge = (int)e.Parameter;
            }
        }
        private void BarraNavegacio_Loaded(object sender, RoutedEventArgs e)
        {

            frmPrincipal.Navigate(typeof(GestorInformes), codiMetge);

        }

        private void NvwBarraNavegacio_ItemInvoked(NavigationView sender, NavigationViewItemInvokedEventArgs args)
        {
            if (args.InvokedItemContainer.Tag.ToString().Equals("Report"))
            {
                openJasperAsync();
                return;
            }
            Type t = typeof(GestorInformes);

            switch (args.InvokedItemContainer.Tag.ToString())
            {

                case "Cites":
                    t = typeof(GestorInformes);
                    break;
                case "Horari":
                    Type type = typeof(GestorHorari);
                    t = type;
                    break;

            }
            frmPrincipal.Navigate(t, codiMetge);

 
        }

        private async Task openJasperAsync()
        {
            DialogJasperReport jsp = new DialogJasperReport();
            await jsp.ShowAsync();
        }
    }
}
