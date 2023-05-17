using GestioInformesHorariClasses;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace GestioInformesHorari.View.MyViewModel
{
    public class FilaDataGrid
    {
        public FilaDataGrid(string hora, List<Especialitat> especialitats, Especialitat dilluns, Especialitat dimarts, Especialitat dimecres, Especialitat dijous, Especialitat divendres, Especialitat dissabte, Especialitat diumenge)
        {
            Hora = hora;
            Especialitats = especialitats;
            Dilluns = dilluns;
            Dimarts = dimarts;
            Dimecres = dimecres;
            Dijous = dijous;
            Divendres = divendres;
            Dissabte = dissabte;
            Diumenge = diumenge;
        }

        public String Hora { get; set; }
        public List<Especialitat> Especialitats { get; set; }
        public Especialitat Dilluns { get; set; }
        public Especialitat Dimarts { get; set; }
        public Especialitat Dimecres { get; set; }
        public Especialitat Dijous { get; set; }
        public Especialitat Divendres { get; set; }
        public Especialitat Dissabte { get; set; }
        public Especialitat Diumenge { get; set; }

    }
}
