using GestioInformesHorari.UI;
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
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Navigation;

namespace GestioInformesHorari.View
{
    public sealed partial class GestorInformes : Page
    {
        private EPGestioInformesHorari epGestio = new EPGestioInformesHorari();
        List<string> dies;
        public GestorInformes()
        {
            this.InitializeComponent();
        }

        private void GestorInformes_Loaded(object sender, RoutedEventArgs e)
        {
            List<Horari> horari = Horari.GenerarHorari();

            // Crear un nou objecte DataTable
            DataTable dt = new DataTable();

            //Afegir la capçelera de les columnes
            List<string> dies = CrearColumnasAmbData(dt);

            UICita c = new UICita();

            // Afegir les files amb les cites
            foreach (var hor in horari)
            {
                DataRow row = dt.NewRow();
                row["Hora"] = hor.Hora;
                if (hor.Dilluns != null) { 
                    row[dies[0]] = hor.Dilluns;
                    row[dies[1]] = c;
                }
                if (hor.Dimarts != null) { row[dies[1]] = hor.Dilluns; }
                if (hor.Dimecres != null) { row[dies[2]] = hor.Dilluns; }
                if (hor.Dijous != null) { row[dies[3]] = hor.Dilluns; }
                if (hor.Divendres != null) { row[dies[4]] = hor.Dilluns; }
                if (hor.Dissabte != null) { row[dies[5]] = hor.Dilluns; }
                if (hor.Diumenge != null) { row[dies[6]] = hor.Dilluns; }
                dt.Rows.Add(row);
            }

            DataTemplate template = new DataTemplate();

            for (int i = 0; i < dt.Columns.Count; i++)
            {
                if (i == 0)
                {
                    gestorInformesDataGrid.Columns.Add(new DataGridTextColumn()
                    {
                        Header = dt.Columns[i].ColumnName,
                        Binding = new Binding { Path = new PropertyPath("[" + i.ToString() + "]") }

                    });
                }
                else
                {
                    gestorInformesDataGrid.Columns.Add(new DataGridTemplateColumn()
                    {
                        Header = dt.Columns[i].ColumnName,
                        CellTemplate = template
                    });
                }
            }

            var collection = new ObservableCollection<object>();
            foreach (DataRow row in dt.Rows)
            {
                collection.Add(row);
            }

            gestorInformesDataGrid.ItemsSource = collection;

            pintarColumnaAvui(gestorInformesDataGrid);
            pintarCites(gestorInformesDataGrid);
            pintarHoresiDies(gestorInformesDataGrid);
            
        }

        private void pintarHoresiDies(DataGrid gestorInformesDataGrid)
        {
            //Estil de cela diferent
            var estil = new Style(typeof(DataGridCell));
            estil.Setters.Add(new Setter(DataGridCell.BackgroundProperty, new SolidColorBrush(Colors.Blue)));

            // Afegim el nou estil a la columna
            gestorInformesDataGrid.ColumnHeaderStyle = estil;
            gestorInformesDataGrid.RowHeaderStyle = estil;

        }

        private void pintarCites(DataGrid gestorInformesDataGrid)
        {
            var estil = new Style(typeof(DataGridCell));
            estil.Setters.Add(new Setter(DataGridCell.BackgroundProperty, new SolidColorBrush(Colors.Blue)));

            

        }


        private void gestorInformesDataGrid_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {

            // Obtenir l'objecte seleccionat
            DataRow selectedCita = (DataRow)gestorInformesDataGrid.SelectedItem;
            int selectedIndex = gestorInformesDataGrid.CurrentColumn.DisplayIndex;

            Cita c = (Cita)selectedCita[1];

        }



        private void InformeText_TextChanged(object sender, TextChangedEventArgs e)
        {
            DesarBtn.IsEnabled = !string.IsNullOrEmpty(InformeText.Text);
        }

        private void pintarColumnaAvui(DataGrid gestorInformesDataGrid)
        {
            //Obtenim el nom de la columna
            DateTime dataActual = DateTime.Today;
            string dataFormatada = dataActual.ToString("dd/MM/yyyy");
            string nomDelDia = dataActual.ToString("dddd", new CultureInfo("ca-ES"));
            nomDelDia = char.ToUpper(nomDelDia[0]) + nomDelDia.Substring(1);
            string nomColAvui = nomDelDia + "\n" + dataFormatada;

            // Obtenim la columna corresponent
            var colAvui = gestorInformesDataGrid.Columns.FirstOrDefault(c => c.Header.ToString() == nomColAvui);

            // Verifiquem si la columna existeix
            if (colAvui != null)
            {
                //Estil de cela diferent
                var estil = new Style(typeof(DataGridCell));
                estil.Setters.Add(new Setter(DataGridCell.BackgroundProperty, new SolidColorBrush(Colors.AliceBlue)));

                // Afegim el nou estil a la columna
                colAvui.CellStyle = estil;
            }
        }

        private List<string> CrearColumnasAmbData(DataTable dt)
        {
            // Agregar la primera columna con el encabezado "Hora"
            dt.Columns.Add("Hora", typeof(string));

            List<string> dies = ObtenirDatesDeLaSetmanaActual();

            // Agregar las columnas restantes con los nombres de los días
            foreach (string dia in dies)
            {
                dt.Columns.Add(dia, typeof(string));
            }
            return dies;
        }

        public static List<string> ObtenirDatesDeLaSetmanaActual()
        {
            List<string> dates = new List<string>();

            // Obtenim la data actual y el dia de la setmana actual
            DateTime dataActual = DateTime.Today;
            DayOfWeek diaActual = dataActual.DayOfWeek;

            // Calculem el primer dia de la setmana
            DateTime primerDiaDeLaSetmana = dataActual.AddDays((-(int)diaActual)+1);

            // Afegim les dates de cada dia de la setmana actual a la llista
            for (int i = 0; i < 7; i++)
            {
                DateTime data = primerDiaDeLaSetmana.AddDays(i);
                string dataFormatada = data.ToString("dd/MM/yyyy");
                string nomDelDia = data.ToString("dddd", new CultureInfo("ca-ES"));
                nomDelDia = char.ToUpper(nomDelDia[0]) + nomDelDia.Substring(1);
                dates.Add(nomDelDia + "\n" + dataFormatada);
            }

            return dates;
        }

        public static List<string> ObtenerFechasDeLaSemanaAnterior()
        {
            List<string> fechas = new List<string>();

            // Obtenemos la fecha actual y el día de la semana actual
            DateTime fechaActual = DateTime.Today;
            DayOfWeek diaActual = fechaActual.DayOfWeek;

            // Calculamos el primer día de la semana anterior
            DateTime primerDiaDeLaSemanaAnterior = fechaActual.AddDays(-(int)diaActual - 7);

            // Agregamos las fechas de cada día de la semana anterior a la lista
            for (int i = 0; i < 7; i++)
            {
                DateTime fecha = primerDiaDeLaSemanaAnterior.AddDays(i);
                string fechaFormateada = fecha.ToString("dd/MM");
                string nombreDelDia = fecha.ToString("dddd", new CultureInfo("es-ES"));
                fechas.Add(nombreDelDia + " " + fechaFormateada);
            }

            return fechas;
        }

        public static List<string> ObtenerFechasDeLaSemanaSiguiente()
        {
            List<string> fechas = new List<string>();

            // Obtenemos la fecha actual y el día de la semana actual
            DateTime fechaActual = DateTime.Today;
            DayOfWeek diaActual = fechaActual.DayOfWeek;

            // Calculamos el primer día de la semana siguiente
            DateTime primerDiaDeLaSemanaSiguiente = fechaActual.AddDays(-(int)diaActual + 7);

            // Agregamos las fechas de cada día de la semana siguiente a la lista
            for (int i = 0; i < 7; i++)
            {
                DateTime fecha = primerDiaDeLaSemanaSiguiente.AddDays(i);
                string fechaFormateada = fecha.ToString("dd/MM");
                string nombreDelDia = fecha.ToString("dddd", new CultureInfo("es-ES"));
                fechas.Add(nombreDelDia + " " + fechaFormateada);
            }

            return fechas;
        }


    }
}
