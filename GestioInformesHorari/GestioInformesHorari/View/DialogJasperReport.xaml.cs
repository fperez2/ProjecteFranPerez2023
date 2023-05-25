using GestioInformesHorariBD;
using GestioInformesHorariClasses;
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

namespace GestioInformesHorari.View
{
    public sealed partial class DialogJasperReport : ContentDialog
    {
        private EPGestioInformesHorari ep = new EPGestioInformesHorari();
        public DialogJasperReport()
        {
            this.InitializeComponent();
        }


        private void ContentDialog_Loaded(object sender, RoutedEventArgs e)
        {
            cmbPacients.ItemsSource = ep.GetAllPacients();

        }


        private void btnCloseDialog_Click(object sender, RoutedEventArgs e)
        {
            this.Hide();
        }

        private void cmbPacients_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (cmbPacients.SelectedIndex != -1)
            {
                Persona p = (Persona)cmbPacients.SelectedItem;
                wbJasperView.Source = new Uri("http://51.68.224.27:8080/jasperserver/flow.html?_flowId=viewReportFlow&_flowId=viewReportFlow&ParentFolderUri=%2Fdam2_fperez2%2Freports&reportUnit=%2Fdam2_fperez2%2Freports%2FHistorialPacients&standAlone=true&j_username=dam2_fperez2&j_password=1025A&nif_pacient=" + p.NIF);
            }
        }
    }
}
