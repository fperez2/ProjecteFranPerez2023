using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace GestioCitesMediquesClasses
{
    class Metge
    {
        private String nif;
        private Persona persona;
        private int codiEmpleat;

        public Metge(string nif, int codiEmpleat)
        {
            NIF = nif;
            CodiEmpleat = codiEmpleat;
        }

        public string NIF { get => nif; set => nif = value; }
        public int CodiEmpleat { get => codiEmpleat; set => codiEmpleat = value; }

        public override bool Equals(object obj)
        {
            return obj is Metge metge &&
                   codiEmpleat == metge.codiEmpleat;
        }

        public override int GetHashCode()
        {
            return 1877310944 + codiEmpleat.GetHashCode();
        }

        public override string ToString()
        {
            return codiEmpleat.ToString();
        }
    }
}
