﻿using GestioInformesHorari.View.MyViewModel;
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
        List<FilaDataGrid> dades;

        public List<Especialitat> Especialitats
        {
            get { return (List<Especialitat>)GetValue(EspecialitatsProperty); }
            set { SetValue(EspecialitatsProperty, value); }
        }
        public static readonly DependencyProperty EspecialitatsProperty =
            DependencyProperty.Register("Especialitats", typeof(List<Especialitat>), typeof(GestorHorari), new PropertyMetadata(new List<Especialitat>()));

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
            Especialitats = epGestio.GetEspecialitats(codiMetge);
            dades = new List<FilaDataGrid>();
            dades = Utils.GenerarLlistaEspecialitats(horari, dades, Especialitats);
            gestorHorariDataGrid.ItemsSource = dades;

        }

        private void Desar_Click(object sender, RoutedEventArgs e)
        {

            int i=0;
        }
    }
}
