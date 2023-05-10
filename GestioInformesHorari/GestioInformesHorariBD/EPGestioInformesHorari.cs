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

    }
}
