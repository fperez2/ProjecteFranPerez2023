using System;

namespace GestioInformesHorari.View
{
    internal class FrameworkElementFactory
    {
        public FrameworkElementFactory(Type type)
        {
            Type = type;
        }

        public Type Type { get; }
    }
}