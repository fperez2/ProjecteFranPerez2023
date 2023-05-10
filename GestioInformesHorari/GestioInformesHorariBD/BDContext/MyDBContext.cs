using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Text;

namespace GestioInformesHorariBD.BDContext
{
    public class MyDBContext : DbContext
    {
        protected override void OnConfiguring(DbContextOptionsBuilder optionBuilder)
        {
            optionBuilder.UseMySQL("Server=127.0.0.1;Database=projecte2023;UID=fran;Password=fran;SslMode=none");

        }
    }
}
