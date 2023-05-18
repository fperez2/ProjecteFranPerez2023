using GestioInformesHorariBD;
using GestioInformesHorariClasses;
using Microsoft.Toolkit.Uwp.UI.Controls;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Data;
using System.Globalization;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Windows.UI;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Media;

namespace GestioInformesHorari.View.MyViewModel
{
    class Utils
    {
        public static void GenerarDataGrid(DataGrid gestorInformesDataGrid, List<Cita> cites, List<Horari> horari, string setmana)
        {
            //Netejar el grid
            gestorInformesDataGrid.Columns.Clear();

            // Crear un nou objecte DataTable
            DataTable dt = new DataTable();

            //Afegir la capçelera de les columnes
            List<string> dies = CrearColumnasAmbData(dt, setmana);

            //Afegir les cites al horari
            AfegirCites(cites, horari, setmana);

            // Afegir les files amb les cites
            foreach (var hor in horari)
            {
                DataRow row = dt.NewRow();
                row["Hora"] = hor.Hora;
                if (hor.Dilluns != null) { row[dies[0]] = hor.Dilluns; }
                if (hor.Dimarts != null) { row[dies[1]] = hor.Dimarts; }
                if (hor.Dimecres != null) { row[dies[2]] = hor.Dimecres; }
                if (hor.Dijous != null) { row[dies[3]] = hor.Dijous; }
                if (hor.Divendres != null) { row[dies[4]] = hor.Divendres; }
                if (hor.Dissabte != null) { row[dies[5]] = hor.Dissabte; }
                if (hor.Diumenge != null) { row[dies[6]] = hor.Diumenge; }
                dt.Rows.Add(row);
            }

            //Crear les columnes
            for (int i = 0; i < dt.Columns.Count; i++)
            {
                gestorInformesDataGrid.Columns.Add(new DataGridTextColumn()
                {
                    Header = dt.Columns[i].ColumnName,
                    Binding = new Binding { Path = new PropertyPath("[" + i.ToString() + "]") }

                });
            }

            //Ficar-ho tot al datagrid
            var rows = new ObservableCollection<object>();
            foreach (DataRow row in dt.Rows)
            {
                rows.Add(row);
            }

            gestorInformesDataGrid.ItemsSource = rows;

            //Alternar color de les files
            Brush lightGray = new SolidColorBrush(Colors.LightGray);
            gestorInformesDataGrid.AlternatingRowBackground = lightGray;
            gestorInformesDataGrid.GridLinesVisibility = DataGridGridLinesVisibility.All;

            for (int i = 1; i < 8; i++)
            {
                // Centrar text columnes
                var column = gestorInformesDataGrid.Columns[i] as DataGridTextColumn;
                var style = new Style(typeof(TextBlock));
                style.Setters.Add(new Setter(TextBlock.TextAlignmentProperty, TextAlignment.Center));
                column.ElementStyle = style;
            }

            if (setmana.Equals("actual")) { pintarColumnaAvui(gestorInformesDataGrid); }
        }

        internal static List<FilaDataGrid> OmplirLLista(List<FilaDataGrid> dades)
        {
            List<FilaDataGrid> fotoLlista = new List<FilaDataGrid>();
            foreach (FilaDataGrid f in dades) 
            {
                FilaDataGrid newf = new FilaDataGrid(f.Hora, f.Especialitats, f.Dilluns, f.Dimarts, f.Dimecres, f.Dijous, f.Divendres, f.Dissabte, f.Diumenge);
                fotoLlista.Add(newf);
            }
            return fotoLlista;
        }

        public static void DesarHorariMetge(EPGestioInformesHorari epGestio, List<FilaDataGrid> dades, List<FilaDataGrid> newDades, int codiMetge)
        {
            for (int i = 0; i < dades.Count; i++)
            {
                FilaDataGrid oldFila = dades[i];
                FilaDataGrid newFila = newDades[i];
                CompararFiles(epGestio, oldFila, newFila, codiMetge);
            }

        }

        public static void CompararFiles(EPGestioInformesHorari epGestio, FilaDataGrid oldFila, FilaDataGrid newFila, int codiMetge)
        {
            string dia;
            string hora = newFila.Hora;
            DateTime dataHora = ObtenirDateTimeAmbHora(hora);
            
            if (oldFila.Dilluns.Codi != newFila.Dilluns.Codi)
            {
                dia = "Dilluns";
                EntradaHorari newEH = new EntradaHorari(codiMetge, dataHora, dia, newFila.Dilluns.Codi);
                EntradaHorari oldEH = new EntradaHorari(codiMetge, dataHora, dia, oldFila.Dilluns.Codi);
                if (oldFila.Dilluns.Codi == 0)
                {
                    epGestio.InsertEntradaHorari(newEH);
                }
                if (oldFila.Dilluns.Codi != 0 && newFila.Dilluns.Codi != 0)
                {
                    epGestio.UpdateEntradaHorari(oldEH, newEH);
                }
                if (newFila.Dilluns.Codi == 0)
                {
                    epGestio.DeleteEntradaHorari(oldEH);
                }
            }

            if (oldFila.Dimarts.Codi != newFila.Dimarts.Codi)
            {
                dia = "Dimarts";
                EntradaHorari newEH = new EntradaHorari(codiMetge, dataHora, dia, newFila.Dimarts.Codi);
                EntradaHorari oldEH = new EntradaHorari(codiMetge, dataHora, dia, oldFila.Dimarts.Codi);
                if (oldFila.Dimarts.Codi == 0)
                {
                    epGestio.InsertEntradaHorari(newEH);
                }
                if (oldFila.Dimarts.Codi != 0 && newFila.Dimarts.Codi != 0)
                {
                    epGestio.UpdateEntradaHorari(oldEH, newEH);
                }
                if (newFila.Dimarts.Codi == 0)
                {
                    epGestio.DeleteEntradaHorari(oldEH);
                }
            }

            if (oldFila.Dimecres.Codi != newFila.Dimecres.Codi)
            {
                dia = "Dimecres";
                EntradaHorari newEH = new EntradaHorari(codiMetge, dataHora, dia, newFila.Dimecres.Codi);
                EntradaHorari oldEH = new EntradaHorari(codiMetge, dataHora, dia, oldFila.Dimecres.Codi);
                if (oldFila.Dimecres.Codi == 0)
                {
                    epGestio.InsertEntradaHorari(newEH);
                }
                if (oldFila.Dimecres.Codi != 0 && newFila.Dimecres.Codi != 0)
                {
                    epGestio.UpdateEntradaHorari(oldEH, newEH);
                }
                if (newFila.Dimecres.Codi == 0)
                {
                    epGestio.DeleteEntradaHorari(oldEH);
                }
            }

            if (oldFila.Dijous.Codi != newFila.Dijous.Codi)
            {
                dia = "Dijous";
                EntradaHorari newEH = new EntradaHorari(codiMetge, dataHora, dia, newFila.Dijous.Codi);
                EntradaHorari oldEH = new EntradaHorari(codiMetge, dataHora, dia, oldFila.Dijous.Codi);
                if (oldFila.Dijous.Codi == 0)
                {
                    epGestio.InsertEntradaHorari(newEH);
                }
                if (oldFila.Dijous.Codi != 0 && newFila.Dijous.Codi != 0)
                {
                    epGestio.UpdateEntradaHorari(oldEH, newEH);
                }
                if (newFila.Dijous.Codi == 0)
                {
                    epGestio.DeleteEntradaHorari(oldEH);
                }
            }

            if (oldFila.Divendres.Codi != newFila.Divendres.Codi)
            {
                dia = "Divendres";
                EntradaHorari newEH = new EntradaHorari(codiMetge, dataHora, dia, newFila.Divendres.Codi);
                EntradaHorari oldEH = new EntradaHorari(codiMetge, dataHora, dia, oldFila.Divendres.Codi);
                if (oldFila.Divendres.Codi == 0)
                {
                    epGestio.InsertEntradaHorari(newEH);
                }
                if (oldFila.Divendres.Codi != 0 && newFila.Divendres.Codi != 0)
                {
                    epGestio.UpdateEntradaHorari(oldEH, newEH);
                }
                if (newFila.Divendres.Codi == 0)
                {
                    epGestio.DeleteEntradaHorari(oldEH);
                }
            }

            if (oldFila.Dissabte.Codi != newFila.Dissabte.Codi)
            {
                dia = "Dissabte";
                EntradaHorari newEH = new EntradaHorari(codiMetge, dataHora, dia, newFila.Dissabte.Codi);
                EntradaHorari oldEH = new EntradaHorari(codiMetge, dataHora, dia, oldFila.Dissabte.Codi);
                if (oldFila.Dissabte.Codi == 0)
                {
                    epGestio.InsertEntradaHorari(newEH);
                }
                if (oldFila.Dissabte.Codi != 0 && newFila.Dissabte.Codi != 0)
                {
                    epGestio.UpdateEntradaHorari(oldEH, newEH);
                }
                if (newFila.Dissabte.Codi == 0)
                {
                    epGestio.DeleteEntradaHorari(oldEH);
                }
            }

            if (oldFila.Diumenge.Codi != newFila.Diumenge.Codi)
            {
                dia = "Diumenge";
                EntradaHorari newEH = new EntradaHorari(codiMetge, dataHora, dia, newFila.Diumenge.Codi);
                EntradaHorari oldEH = new EntradaHorari(codiMetge, dataHora, dia, oldFila.Diumenge.Codi);
                if (oldFila.Diumenge.Codi == 0)
                {
                    epGestio.InsertEntradaHorari(newEH);
                }
                if (oldFila.Diumenge.Codi != 0 && newFila.Diumenge.Codi != 0)
                {
                    epGestio.UpdateEntradaHorari(oldEH, newEH);
                }
                if (newFila.Diumenge.Codi == 0)
                {
                    epGestio.DeleteEntradaHorari(oldEH);
                }
            }
            
        }

        public static List<FilaDataGrid> GenerarLlistaEspecialitats(List<Horari> horari, List<FilaDataGrid> dades, List<Especialitat> especialitats, List<EntradaHorari> horariMetge)
        {
            Especialitat dilluns = new Especialitat();
            Especialitat dimarts = new Especialitat();
            Especialitat dimecres = new Especialitat();
            Especialitat dijous = new Especialitat();
            Especialitat divendres = new Especialitat();
            Especialitat dissabte = new Especialitat();
            Especialitat diumenge = new Especialitat();
            foreach (Horari hor in horari)
            {
                dilluns = especialitats[0];
                dimarts = especialitats[0];
                dimecres = especialitats[0];
                dijous = especialitats[0];
                divendres = especialitats[0];
                dissabte = especialitats[0];
                diumenge = especialitats[0];

                foreach (EntradaHorari eh in horariMetge) 
                {

                    TimeSpan horaEH = eh.Hora.TimeOfDay;
                    int hores = horaEH.Hours;
                    int minuts = horaEH.Minutes;
                    string shores = hores.ToString();
                    string sminuts = minuts.ToString();

                    if (minuts != 30) 
                    {
                        sminuts = "00";                    
                    }
                    string hora = shores + ":" + sminuts;

                    if (hora.Equals(hor.Hora))
                    {
                        switch (eh.DiaSetmana)
                        {
                            case "Dilluns":
                                dilluns = comprovaEspecilitat(especialitats, eh.CodiEspecialitat);
                                break;
                            case "Dimarts":
                                dimarts = comprovaEspecilitat(especialitats, eh.CodiEspecialitat);
                                break;
                            case "Dimecres":
                                dimecres = comprovaEspecilitat(especialitats, eh.CodiEspecialitat);
                                break;
                            case "Dijous":
                                dijous = comprovaEspecilitat(especialitats, eh.CodiEspecialitat);
                                break;
                            case "Divendres":
                                divendres = comprovaEspecilitat(especialitats, eh.CodiEspecialitat);
                                break;
                            case "Dissabte":
                                dissabte = comprovaEspecilitat(especialitats, eh.CodiEspecialitat);
                                break;
                            case "Diumenge":
                                diumenge = comprovaEspecilitat(especialitats, eh.CodiEspecialitat);
                                break;
                        }
                    }
                }
                FilaDataGrid fila = new FilaDataGrid(hor.Hora, especialitats, dilluns, dimarts, dimecres, dijous, divendres, dissabte, diumenge);
                dades.Add(fila);
            }
            return dades;
        }

        public static Especialitat comprovaEspecilitat(List<Especialitat> especialitats, int codiEspecialitat)
        {
            Especialitat esp = null;
            foreach (Especialitat e in especialitats)
            {
                if (e.Codi == codiEspecialitat) { esp = e; }
            }
            return esp;
        }

        public static void AfegirCites(List<Cita> cites, List<Horari> horari, string setmana)
        {
            foreach (Cita cita in cites) {
                string horaString = cita.DataHora.ToString("HH:mm");
                foreach (Horari hor in horari)
                {
                    if (hor.Hora.Equals(horaString))
                    {
                        switch (cita.DataHora.DayOfWeek)
                        {
                            case DayOfWeek.Monday:
                                hor.Dilluns = cita;
                                break;
                            case DayOfWeek.Tuesday:
                                hor.Dimarts = cita;
                                break;
                            case DayOfWeek.Wednesday:
                                hor.Dimecres = cita;
                                break;
                            case DayOfWeek.Thursday:
                                hor.Dijous = cita;
                                break;
                            case DayOfWeek.Friday:
                                hor.Divendres = cita;
                                break;
                            case DayOfWeek.Saturday:
                                hor.Dissabte = cita;
                                break;
                            case DayOfWeek.Sunday:
                                hor.Diumenge = cita;
                                break;
                        }
                    }
                }
            }
        }

        public static void pintarColumnaAvui(DataGrid gestorInformesDataGrid)
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

        public static List<string> CrearColumnasAmbData(DataTable dt, string setmana)
        {
            // Agregar la primera columna con el encabezado "Hora"
            dt.Columns.Add("Hora", typeof(string));

            List<string> dies = new List<string>();

            switch (setmana)
            {
                case "actual":
                    dies = ObtenirDatesDeLaSetmanaActual();
                    break;
                case "anterior":
                    dies = ObtenirDatesDeLaSetmanaAnterior();
                    break;
                case "seguent":
                    dies = ObtenirDatesDeLaSetmanaSeguent();
                    break;
            }

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
            DateTime primerDiaDeLaSetmana = dataActual.AddDays((-(int)diaActual) + 1);

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

        public static List<string> ObtenirDatesDeLaSetmanaAnterior()
        {
            List<string> dates = new List<string>();

            // Obtenim la data actual y el dia de la setmana actual
            DateTime dataActual = DateTime.Today;
            DayOfWeek diaActual = dataActual.DayOfWeek;

            // Calculem el primer dia de la setmana anterior
            DateTime primerDiaDeLaSetmanaAnterior = dataActual.AddDays(-(int)diaActual - 6);

            // Afegim les dates de cada dia de la setmana anterior a la llista
            for (int i = 0; i < 7; i++)
            {
                DateTime data = primerDiaDeLaSetmanaAnterior.AddDays(i);
                string dataFormatada = data.ToString("dd/MM/yyyy");
                string nomDelDia = data.ToString("dddd", new CultureInfo("ca-ES"));
                nomDelDia = char.ToUpper(nomDelDia[0]) + nomDelDia.Substring(1);
                dates.Add(nomDelDia + "\n" + dataFormatada);
            }

            return dates;
        }

        public static List<string> ObtenirDatesDeLaSetmanaSeguent()
        {
            List<string> dates = new List<string>();

            // Obtenim la data actual y el dia de la setmana actual
            DateTime dataActual = DateTime.Today;
            DayOfWeek diaActual = dataActual.DayOfWeek;

            // Calculem el primer dia de la setmana seguent
            DateTime primerDiaDeLaSetmanaSeguent = dataActual.AddDays(-(int)diaActual + 8);

            // Afegim les dates de cada dia de la setmana seguent a la llista
            for (int i = 0; i < 7; i++)
            {
                DateTime data = primerDiaDeLaSetmanaSeguent.AddDays(i);
                string dataFormatada = data.ToString("dd/MM/yyyy");
                string nomDelDia = data.ToString("dddd", new CultureInfo("ca-ES"));
                nomDelDia = char.ToUpper(nomDelDia[0]) + nomDelDia.Substring(1);
                dates.Add(nomDelDia + "\n" + dataFormatada);
            }

            return dates;
        }

        public static string CalcularEdat(DateTime dataNaix)
        {
            int edat = DateTime.Today.Year - dataNaix.Year;

            // Restar un año si aún no ha pasado el cumpleaños de este año
            if (dataNaix > DateTime.Today.AddYears(-edat))
                edat--;

            return edat.ToString();
        }

        public static bool Ultimes48Horas(DateTime data)
        {
            DateTime ara = DateTime.Now;
            DateTime iniciRang = ara.AddDays(-1).Date; // Inici del rang: fa dos dies a las 00:00
            DateTime fiRang = ara.Date.AddDays(1).AddTicks(-1); // Fi del rang: avui a las 23:59:59.9999999

            if (data >= iniciRang && data <= fiRang)
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        public static DateTime ObtenirDateTimeAmbHora(string horaString)
        {
            DateTime dataBase = new DateTime(2000, 1, 1);
            TimeSpan hora = TimeSpan.Parse(horaString);
            DateTime resultat = dataBase.Date + hora;
            return resultat;
        }

    }
}
