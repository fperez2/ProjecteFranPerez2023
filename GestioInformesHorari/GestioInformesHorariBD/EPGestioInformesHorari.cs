using System;
using System.Data;
using System.Data.Common;
using GestioInformesHorariClasses;
using GestioInformesHorariBD.BDContext;
using InterficieGestioInformesHorari;
using Microsoft.EntityFrameworkCore;

namespace GestioInformesHorariBD
{
    public class EPGestioInformesHorari
    {
        public string Login(string login, string password)
        {
            try
            {
                using (MyDBContext context = new MyDBContext())
                {
                    using (var connexio = context.Database.GetDbConnection())
                    {
                        connexio.Open();

                        using (DbCommand consulta = connexio.CreateCommand())
                        {

                            consulta.CommandText = $@"select p.Persona_NIF from Persona p where p.Persona_Login = @LOGIN and p.Persona_Password = @PASSWORD";
                            DBUtil.crearParametre(consulta, "@LOGIN", login, DbType.String);
                            DBUtil.crearParametre(consulta, "@PASSWORD", password, DbType.String);
                            string resposta = (string)consulta.ExecuteScalar();

                            return resposta;
                        }
                    }
                }
            }
            catch (Exception)
            {
            }
            return null;
        }
        public string GetNameByNIF(string NIF)
        {
            try
            {
                using (MyDBContext context = new MyDBContext())
                {
                    using (var connexio = context.Database.GetDbConnection())
                    {
                        connexio.Open();

                        using (DbCommand consulta = connexio.CreateCommand())
                        {

                            consulta.CommandText = $@"select p.Persona_Nom, p.Persona_Cognom1, p.Persona_Cognom2, p.Persona_DataNaix  from Persona p where p.Persona_NIF = @NIF";
                            DBUtil.crearParametre(consulta, "@NIF", NIF, DbType.String);
                            DbDataReader resposta = consulta.ExecuteReader();

                            string nom = (string)resposta.GetString(0);
                            string cognom1 = (string)resposta.GetString(1); 
                            string cognom2 = (string)resposta.GetString(2); 
                            string dataNaix = (string)resposta.GetString(3);

                            string nomComplet = nom + " " + cognom1 + (cognom2.Equals("null") ? "" : " " + cognom2);

                            return nomComplet + ";" + dataNaix;
                        }
                    }
                }
            }
            catch (Exception)
            {
            }
            return null;
        }

    }
}
