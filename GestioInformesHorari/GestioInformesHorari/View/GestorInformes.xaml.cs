using GestioInformesHorari.View.MyViewModel;
using GestioInformesHorariBD;
using GestioInformesHorariClasses;
using Microsoft.Toolkit.Uwp.UI.Controls;
using Microsoft.Toolkit.Uwp.UI.Controls.Primitives;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Data;
using System.Globalization;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.UI;
using Windows.UI.Composition;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Markup;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Navigation;

namespace GestioInformesHorari.View
{
    public sealed partial class GestorInformes : Page
    {
        private EPGestioInformesHorari epGestio = new EPGestioInformesHorari();
        int codiMetge;
        List<Horari> horari;
        public GestorInformes()
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
        private void GestorInformes_Loaded(object sender, RoutedEventArgs e)
        {
            horari = Horari.GenerarHorari();
            List<Cita> cites = epGestio.GetCitesActualWeek(codiMetge);
            string setmana = "actual";

            Utils.GenerarDataGrid(gestorInformesDataGrid, cites, horari, setmana);
            PrevBtn.IsEnabled = true;
            ActualBtn.IsEnabled = false;
            NextBtn.IsEnabled = true;
        }

        private void Actual_Click(object sender, RoutedEventArgs e)
        {
            horari = Horari.GenerarHorari();
            List<Cita> cites = epGestio.GetCitesActualWeek(codiMetge);
            string setmana = "actual";

            Utils.GenerarDataGrid(gestorInformesDataGrid, cites, horari, setmana);
            PrevBtn.IsEnabled = true;
            ActualBtn.IsEnabled = false;
            NextBtn.IsEnabled = true;
        }
        private void Previous_Click(object sender, RoutedEventArgs e)
        {
            horari = Horari.GenerarHorari();
            List<Cita> cites = epGestio.GetCitesPreviousWeek(codiMetge);
            string setmana = "anterior";

            Utils.GenerarDataGrid(gestorInformesDataGrid, cites, horari, setmana);
            PrevBtn.IsEnabled = false;
            ActualBtn.IsEnabled = true;
            NextBtn.IsEnabled = true;
        }

        private void Next_Click(object sender, RoutedEventArgs e)
        {
            horari = Horari.GenerarHorari();
            List<Cita> cites = epGestio.GetCitesNextWeek(codiMetge);
            string setmana = "seguent";

            Utils.GenerarDataGrid(gestorInformesDataGrid, cites, horari, setmana);
            PrevBtn.IsEnabled = true;
            ActualBtn.IsEnabled = true;
            NextBtn.IsEnabled = false;
        }
        private void gestorInformesDataGrid_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            // Obtenir l'objecte seleccionat
            int selectedIndex = 0;
            DataRow selectedCita = (DataRow)gestorInformesDataGrid.SelectedItem;
            if(selectedCita != null)
            {
                selectedIndex = gestorInformesDataGrid.CurrentColumn.DisplayIndex;
            }

            if (selectedCita != null && selectedIndex > 0 && selectedCita[selectedIndex].Equals("X"))
            {
                Cita c = new Cita();
                foreach (Horari hor in horari)
                {
                    if (hor.Hora.Equals(selectedCita[0]))
                    {

                        switch (selectedIndex)
                        {
                            case 1:
                                c = hor.Dilluns;
                                break;
                            case 2:
                                c = hor.Dimarts;
                                break;
                            case 3:
                                c = hor.Dimecres;
                                break;
                            case 4:
                                c = hor.Dijous;
                                break;
                            case 5:
                                c = hor.Divendres;
                                break;
                            case 6:
                                c = hor.Dissabte;
                                break;
                            case 7:
                                c = hor.Diumenge;
                                break;
                        }
                    }
                }
                
                string nomIdataNaix = epGestio.GetNameByNIF(c.NIF);
                string[] dadesPersona = nomIdataNaix.Split(';');
                DateTime dataNaix = DateTime.Parse(dadesPersona[1]);

                PacientText.Text = dadesPersona[0];
                EdatText.Text = Utils.CalcularEdat(dataNaix) + " anys";
                InformeText.Text =  c.Informe != null ? c.Informe : "";
                DesatText.Text = "";

            }
            else 
            {
                PacientText.Text = "";
                EdatText.Text = "";
                InformeText.Text = "";
                DesatText.Text = "";
            }
        }

        private void InformeText_TextChanged(object sender, TextChangedEventArgs e)
        {
            DesarBtn.IsEnabled = !string.IsNullOrEmpty(InformeText.Text);
            DesatText.Text = "";
        }
        private void Desar_Click(object sender, RoutedEventArgs e)
        {
            // Obtenir l'objecte seleccionat
            int selectedIndex = 0;
            DataRow selectedCita = (DataRow)gestorInformesDataGrid.SelectedItem;
            if (selectedCita != null)
            {
                selectedIndex = gestorInformesDataGrid.CurrentColumn.DisplayIndex;
            }

            if (selectedCita != null && selectedIndex > 0 && selectedCita[selectedIndex].Equals("X"))
            {
                Cita c = new Cita();
                foreach (Horari hor in horari)
                {
                    if (hor.Hora.Equals(selectedCita[0]))
                    {

                        switch (selectedIndex)
                        {
                            case 1:
                                c = hor.Dilluns;
                                break;
                            case 2:
                                c = hor.Dimarts;
                                break;
                            case 3:
                                c = hor.Dimecres;
                                break;
                            case 4:
                                c = hor.Dijous;
                                break;
                            case 5:
                                c = hor.Divendres;
                                break;
                            case 6:
                                c = hor.Dissabte;
                                break;
                            case 7:
                                c = hor.Diumenge;
                                break;
                        }
                    }
                }

                if ((c.Informe == null || !c.Informe.Equals(InformeText.Text)) && Utils.Ultimes48Horas(c.DataHora)) 
                {
                    c.Informe = InformeText.Text;
                    epGestio.UpdateInformeCita(c);
                    DesatText.Text = "Desat!";
                }
                if (c.Informe != null && c.Informe.Equals(InformeText.Text)) { DesatText.Text = "Aquest informe ja esta desat!"; }
                if (!Utils.Ultimes48Horas(c.DataHora)) { DesatText.Text = "Aquest informe no es pot editar!"; }
            }
        }
    }
}
