// JediTermMain.java
package com.jediterm.app;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;

import org.jetbrains.annotations.NotNull;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;

@Metadata(mv= {1,9,0},k=1,d1= {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0002J\u001b\u0010\u0005\u001a\u00020\u00042\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u0007¢\u0006\u0002\u0010\t¨\u0006\n"},d2= {"Lcom/jediterm/app/JediTermMain;","","()V","configureJavaUtilLogging","","main","arg","","","([Ljava/lang/String;)V","JediTerm"})
public final class JediTermMain{
  @NotNull
  public static final JediTermMain INSTANCE;
  @JvmStatic
  public static final void main(@NotNull String[] arg) {
    Intrinsics.checkNotNullParameter(arg,"arg");
    INSTANCE.configureJavaUtilLogging();
    SwingUtilities.invokeLater((Runnable)null);
  }
  private final void configureJavaUtilLogging() {
    String format="[%1$tF %1$tT] [%4\\$-7s] %5$s %n";
    LogManager var10000=LogManager.getLogManager();
    String var2="java.util.logging.SimpleFormatter.format="+format;
    Charset var3=Charsets.UTF_8;
    byte[] var10003=var2.getBytes(var3);
    Intrinsics.checkNotNullExpressionValue(var10003,"this as java.lang.String).getBytes(charset)");
    try {
      var10000.readConfiguration((InputStream)(new ByteArrayInputStream(var10003)));
    }catch(SecurityException e) {
      e.printStackTrace();
    }catch(IOException e) {
      e.printStackTrace();
    }
    Logger rootLogger=Logger.getLogger("");
    ConsoleHandler var9=new ConsoleHandler();
    int var5=0;
    var9.setLevel(Level.ALL);
    Unit var7=Unit.INSTANCE;
    rootLogger.addHandler((Handler)var9);
    Intrinsics.checkNotNullExpressionValue(rootLogger,"rootLogger");
    rootLogger.setLevel(Level.INFO);
  }
  private JediTermMain() {}
  static {
    JediTermMain var0=new JediTermMain();
    INSTANCE=var0;
  }
}