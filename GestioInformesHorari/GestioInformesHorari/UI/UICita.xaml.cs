using GestioInformesHorariClasses;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Navigation;

namespace GestioInformesHorari.UI
{
    public sealed partial class UICita : UserControl
    {
        public UICita()
        {
            this.InitializeComponent();
        }

        public event EventHandler clickada;

        public Cita esCita
        {
            get { return (Cita)GetValue(esCitaProperty); }
            set { SetValue(esCitaProperty, value); }
        }

        public static readonly DependencyProperty esCitaProperty =
            DependencyProperty.Register("esCita", typeof(Cita), typeof(UICita), new PropertyMetadata(null, esCitaChangedCallbackStatic));

        private static void esCitaChangedCallbackStatic(DependencyObject d, DependencyPropertyChangedEventArgs e)
        {
            UICita c = (UICita)d;
            c.esCitaChangedCallback(e);
        }

        private void esCitaChangedCallback(DependencyPropertyChangedEventArgs e)
        {
            

        }

    }
}
