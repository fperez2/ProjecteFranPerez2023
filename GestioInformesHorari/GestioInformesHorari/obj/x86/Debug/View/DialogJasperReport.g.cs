﻿#pragma checksum "C:\Users\franc\Desktop\ProjecteFranPerez2023\ProjecteFranPerez2023\GestioInformesHorari\GestioInformesHorari\View\DialogJasperReport.xaml" "{8829d00f-11b8-4213-878b-770e8597ac16}" "ACD8CCC5CD0C010A0F18E10AAED6D16D17A589D4191832D4603438BFD178046C"
//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace GestioInformesHorari.View
{
    partial class DialogJasperReport : 
        global::Windows.UI.Xaml.Controls.ContentDialog, 
        global::Windows.UI.Xaml.Markup.IComponentConnector,
        global::Windows.UI.Xaml.Markup.IComponentConnector2
    {
        /// <summary>
        /// Connect()
        /// </summary>
        [global::System.CodeDom.Compiler.GeneratedCodeAttribute("Microsoft.Windows.UI.Xaml.Build.Tasks"," 10.0.19041.685")]
        [global::System.Diagnostics.DebuggerNonUserCodeAttribute()]
        public void Connect(int connectionId, object target)
        {
            switch(connectionId)
            {
            case 1: // View\DialogJasperReport.xaml line 1
                {
                    global::Windows.UI.Xaml.Controls.ContentDialog element1 = (global::Windows.UI.Xaml.Controls.ContentDialog)(target);
                    ((global::Windows.UI.Xaml.Controls.ContentDialog)element1).Loaded += this.ContentDialog_Loaded;
                }
                break;
            case 2: // View\DialogJasperReport.xaml line 25
                {
                    this.wbJasperView = (global::Windows.UI.Xaml.Controls.WebView)(target);
                }
                break;
            case 3: // View\DialogJasperReport.xaml line 22
                {
                    this.cmbPacients = (global::Windows.UI.Xaml.Controls.ComboBox)(target);
                    ((global::Windows.UI.Xaml.Controls.ComboBox)this.cmbPacients).SelectionChanged += this.cmbPacients_SelectionChanged;
                }
                break;
            case 4: // View\DialogJasperReport.xaml line 23
                {
                    this.btnCloseDialog = (global::Windows.UI.Xaml.Controls.Button)(target);
                    ((global::Windows.UI.Xaml.Controls.Button)this.btnCloseDialog).Click += this.btnCloseDialog_Click;
                }
                break;
            default:
                break;
            }
            this._contentLoaded = true;
        }

        /// <summary>
        /// GetBindingConnector(int connectionId, object target)
        /// </summary>
        [global::System.CodeDom.Compiler.GeneratedCodeAttribute("Microsoft.Windows.UI.Xaml.Build.Tasks"," 10.0.19041.685")]
        [global::System.Diagnostics.DebuggerNonUserCodeAttribute()]
        public global::Windows.UI.Xaml.Markup.IComponentConnector GetBindingConnector(int connectionId, object target)
        {
            global::Windows.UI.Xaml.Markup.IComponentConnector returnValue = null;
            return returnValue;
        }
    }
}

