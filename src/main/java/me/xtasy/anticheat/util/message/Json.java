package me.xtasy.anticheat.util.message;

import java.util.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import java.lang.reflect.*;

public class Json
{
    public class AMText
    {
        final Json this$0;
        private Map<String, Map.Entry<String, String>> Modifiers;
        private String Message;

        
        AMText addHoverText(final String... array) {
            final String s = "hoverEvent";
            final String s2 = "show_text";
            StringBuilder sb;
            if (array.length == 1) {
                sb = new StringBuilder("{\"text\":\"" + array[0] + "\"}");

            }
            else {
                final StringBuilder sb2 = new StringBuilder("{\"text\":\"\",\"extra\":[");
                final int length = array.length;
                int i = 0;
                while (i < length) {
                    sb2.append("{\"text\":\"").append(array[i]).append("\"},");
                    ++i;

                }
                sb = new StringBuilder(sb2.substring(0, sb2.length() - 1));
                sb.append("]}");
            }
            this.Modifiers.put(s, new AbstractMap.SimpleEntry<String, String>(s2, sb.toString()));
            return this;
        }
        
        public void setClickEvent(final ClickableType clickableType, final String s) {
            this.Modifiers.put("clickEvent", new AbstractMap.SimpleEntry<String, String>(clickableType.Action, "\"" + s + "\""));
        }
        
        String getFormattedMessage() {
            final StringBuilder sb = new StringBuilder("{\"text\":\"" + this.Message + "\"");
            for (final String s : this.Modifiers.keySet()) {
                final Map.Entry<String, String> entry = this.Modifiers.get(s);
                sb.append(",\"").append(s).append("\":{\"action\":\"").append(entry.getKey()).append("\",\"value\":").append(entry.getValue()).append("}");

            }
            sb.append("}");
            return sb.toString();
        }
        
        AMText(final Json this$0, final String message) {
            this.this$0 = this$0;
            this.Message = "";
            this.Modifiers = new HashMap<String, Map.Entry<String, String>>();
            this.Message = message;
        }
    }
    
    public enum ClickableType
    {
        RunCommand("run_command"),
        SuggestCommand("suggest_command"),
        OpenURL("open_url");
        public String Action;
        private static final ClickableType[] $VALUES;
        

        
        private ClickableType(final String action) {
            this.Action = action;
        }
        
        static {
            $VALUES = new ClickableType[] { ClickableType.RunCommand, ClickableType.SuggestCommand, ClickableType.OpenURL };
        }
    }
    
    public static class UtilActionMessage
    {
        private List<AMText> Text;

        
        public UtilActionMessage() {
            this.Text = new ArrayList<AMText>();
        }
        
        private String getFormattedMessage() {
            final StringBuilder sb = new StringBuilder("[\"\",");
            final Iterator<AMText> iterator = this.Text.iterator();
            while (iterator.hasNext()) {
                sb.append(iterator.next().getFormattedMessage()).append(",");

            }
            final StringBuilder sb2 = new StringBuilder(sb.substring(0, sb.length() - 1));
            sb2.append("]");
            return sb2.toString();
        }
        
        private void sendPacket(final Player player, final Object o) {
            try {
                final Object invoke = player.getClass().getMethod("getHandle", (Class<?>[])new Class[0]).invoke(player, new Object[0]);
                final Object value = invoke.getClass().getField("playerConnection").get(invoke);
                value.getClass().getMethod("sendPacket", this.getNMSClass("Packet")).invoke(value, o);

            }
            catch (Exception ex) {}
        }
        
        public AMText addText(final String s) {
            final AMText amText = new AMText(s);
            this.Text.add(amText);
            return amText;
        }
        
        private Class<?> getNMSClass(final String s) {
            final String s2 = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
            try {
                return Class.forName("net.minecraft.server." + s2 + "." + s);
            }
            catch (ClassNotFoundException ex) {
                return null;
            }
        }
        
        public void sendToPlayer(final Player player) {
            try {
                final String s = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
                final Constructor<?> constructor = this.getNMSClass("PacketPlayOutChat").getConstructor(this.getNMSClass("IChatBaseComponent"));
                Object o;
                if (s.contains("v1_7") || s.contains("1_8_R1")) {
                    o = this.getNMSClass("ChatSerializer").getMethod("a", String.class).invoke(null, this.getFormattedMessage());

                }
                else {
                    o = this.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, this.getFormattedMessage());
                }
                this.sendPacket(player, constructor.newInstance(o));

            }
            catch (Exception ex) {}
        }
        
        public class AMText
        {
            private Map<String, Map.Entry<String, String>> Modifiers;
            private String Message;

            
            public AMText addHoverText(final String... array) {
                final String s = "hoverEvent";
                final String s2 = "show_text";
                StringBuilder sb;
                if (array.length == 1) {
                    sb = new StringBuilder("{\"text\":\"" + array[0] + "\"}");

                }
                else {
                    final StringBuilder sb2 = new StringBuilder("{\"text\":\"\",\"extra\":[");
                    final int length = array.length;
                    int i = 0;
                    while (i < length) {
                        sb2.append("{\"text\":\"").append(array[i]).append("\"},");
                        ++i;

                    }
                    sb = new StringBuilder(sb2.substring(0, sb2.length() - 1));
                    sb.append("]}");
                }
                this.Modifiers.put(s, new AbstractMap.SimpleEntry<String, String>(s2, sb.toString()));
                return this;
            }
            
            String getFormattedMessage() {
                final StringBuilder sb = new StringBuilder("{\"text\":\"" + this.Message + "\"");
                for (final String s : this.Modifiers.keySet()) {
                    final Map.Entry<String, String> entry = this.Modifiers.get(s);
                    sb.append(",\"").append(s).append("\":{\"action\":\"").append(entry.getKey()).append("\",\"value\":").append(entry.getValue()).append("}");

                }
                sb.append("}");
                return sb.toString();
            }
            
            public void setClickEvent(final ClickableType clickableType, final String s) {
                this.Modifiers.put("clickEvent", new AbstractMap.SimpleEntry<String, String>(clickableType.Action, "\"" + s + "\""));
            }
            
            AMText(final String message) {

                this.Message = "";
                this.Modifiers = new HashMap<String, Map.Entry<String, String>>();
                this.Message = message;
            }
        }
        
        public enum ClickableType
        {
            OpenURL("open_url"), 
            RunCommand("run_command"), 
            SuggestCommand("suggest_command");
            
            private static final ClickableType[] $VALUES;
            public String Action;
            
            private ClickableType(final String action) {
                this.Action = action;
            }
            
            static {
                $VALUES = new ClickableType[] { ClickableType.RunCommand, ClickableType.SuggestCommand, ClickableType.OpenURL };
            }
        }
    }
}
