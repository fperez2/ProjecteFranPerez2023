using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace GestioCitesMediquesClasses
{
    class MetgeEspecialitat
    {
        private int codiMetge;
        private int codiEspecialitat;

        private Metge codiEmpleat;
        private Especialitat codi;


        public MetgeEspecialitat(int codiMetge, int codiEspecialitat)
        {
            CodiMetge = codiMetge;
            CodiEspecialitat = codiEspecialitat;
        }

        public int CodiMetge { get => codiMetge; set => codiMetge = value; }
        public int CodiEspecialitat { get => codiEspecialitat; set => codiEspecialitat = value; }

        public override bool Equals(object obj)
        {
            return obj is MetgeEspecialitat me &&
                   codiMetge == me.codiMetge && 
                   codiEspecialitat == me.codiEspecialitat;
        }

        public override int GetHashCode()
        {
            return 1877310944 + codiMetge.GetHashCode() + codiEspecialitat.GetHashCode();
        }

        public override string ToString()
        {
            return codiMetge.ToString() + " " + codiEspecialitat.ToString();
        }
    }
}
