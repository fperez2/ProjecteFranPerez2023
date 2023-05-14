using GestioInformesHorari.View.MyViewModel;
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
    public sealed partial class GestorHorari : Page
    {
        private EPGestioInformesHorari epGestio = new EPGestioInformesHorari();
        int codiMetge;
        List<Horari> horari;
        public GestorHorari()
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
        private void GestorHoraris_Loaded(object sender, RoutedEventArgs e)
        {
            horari = Horari.GenerarHorari();
            List<Especialitat> especialitats = epGestio.GetEspecialitats(codiMetge);

            Utils.GenerarComboBoxDataGrid(gestorHorariDataGrid, horari, especialitats);

        }

        private void gestorHorariDataGrid_SelectionChanged(object sender, SelectionChangedEventArgs e)
        { }
    }
}
