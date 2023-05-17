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
            Dilluns = especialitats[0];
            Dimarts = especialitats[0];
            Dimecres = especialitats[0];
            Dijous = especialitats[0];
            Divendres = especialitats[0];
            Dissabte = especialitats[0];
            Diumenge = especialitats[0];
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
