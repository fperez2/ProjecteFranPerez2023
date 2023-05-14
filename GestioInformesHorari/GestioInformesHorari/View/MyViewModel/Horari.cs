using GestioInformesHorariClasses;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace GestioInformesHorari.View.MyViewModel
{

    public class Horari
    {
        public enum Dies { Dilluns, Dimarts, Dimecres, Dijous, Divendres, Dissabte, Diumenge }
        public enum Hores { H07M00, H07M30, H08M00, H08M30, H09M00, H09M30, H10M00, H10M30, H11M00, H11M30, H12M00, H12M30,
            H13M00, H13M30, H14M00, H14M30, H15M00, H15M30, H16M00, H16M30, H17M00, H17M30, H18M00, H18M30, H19M00, H19M30, H20M00, H20M30, H21M00 }

        public string Hora { get; set; }
        public Cita Dilluns { get; set; }
        public Cita Dimarts { get; set; }
        public Cita Dimecres { get; set; }
        public Cita Dijous { get; set; }
        public Cita Divendres { get; set; }
        public Cita Dissabte { get; set; }
        public Cita Diumenge { get; set; }


        public Horari(string Hora)
        {
            this.Hora = Hora;
        }
        public Horari(string Hora, Cita Dilluns, Cita Dimarts, Cita Dimecres, Cita Dijous, Cita Divendres, Cita Dissabte, Cita Diumenge)
        {
            this.Hora = Hora;
            if (Dilluns != null) {
                this.Dilluns = Dilluns;
            }
            if (Dimarts != null)
            {
                this.Dimarts = Dimarts;
            }
            if (Dimecres != null)
            {
                this.Dimecres = Dimecres;
            }
            if (Dijous != null)
            {
                this.Dijous = Dijous;
            }
            if (Divendres != null)
            {
                this.Divendres = Divendres;
            }
            if (Dissabte != null)
            {
                this.Dissabte = Dissabte;
            }
            if (Diumenge != null)
            {
                this.Diumenge = Diumenge;
            }
        }

        public static List<Horari> GenerarHorari()
        {
            var horariList = new List<Horari>();
            foreach (Hores h in Enum.GetValues(typeof(Hores)))
            {
                var horari = new Horari(convertHora(h), null, null, null, null, null, null, null);
                horariList.Add(horari);
            }
            
            return horariList;
        }

        private static string convertHora(Hores h)
        {
            string hora = h.ToString();

            // Obtenir el número d'horas i minuts de la string
            int horas = int.Parse(hora.Substring(1, hora.Contains("M") ? hora.IndexOf("M") - 1 : 1));
            int minutos = int.Parse(hora.Substring(hora.Contains("M") ? hora.IndexOf("M") + 1 : 3, 2));

            // Combinar les hores i els minuts en una string amb format HH:MM
            return $"{horas}:{minutos:D2}";
        }
    }

}
