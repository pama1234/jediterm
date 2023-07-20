// JediTerm.java
package com.jediterm.app;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import com.jediterm.core.Platform;
import com.jediterm.pty.PtyProcessTtyConnector;
import com.jediterm.terminal.LoggingTtyConnector;
import com.jediterm.terminal.TtyConnector;
import com.jediterm.terminal.model.LinesBuffer;
import com.jediterm.terminal.model.TerminalTextBuffer;
import com.jediterm.terminal.model.hyperlinks.HyperlinkFilter;
import com.jediterm.terminal.ui.JediTermWidget;
import com.jediterm.terminal.ui.TerminalWidget;
import com.jediterm.terminal.ui.UIUtil;
import com.jediterm.terminal.ui.settings.DefaultTabbedSettingsProvider;
import com.jediterm.terminal.ui.settings.SettingsProvider;
import com.jediterm.terminal.ui.settings.TabbedSettingsProvider;
import com.jediterm.ui.AbstractTerminalFrame;
import com.pty4j.PtyProcess;
import com.pty4j.PtyProcessBuilder;

import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;

@Metadata(mv= {1,9,0},k=1,d1= {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u000bB\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0014J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0014J\b\u0010\t\u001a\u00020\nH\u0016¨\u0006\f"},d2= {"Lcom/jediterm/app/JediTerm;","Lcom/jediterm/ui/AbstractTerminalFrame;","()V","createTabbedTerminalWidget","Lcom/jediterm/app/JediTabbedTerminalWidget;","createTerminalWidget","Lcom/jediterm/app/JediTerminalWidget;","settingsProvider","Lcom/jediterm/terminal/ui/settings/TabbedSettingsProvider;","createTtyConnector","Lcom/jediterm/terminal/TtyConnector;","LoggingPtyProcessTtyConnector","JediTerm"})
public final class JediTerm extends AbstractTerminalFrame{
  @NotNull
  protected JediTabbedTerminalWidget createTabbedTerminalWidget() {
    return (JediTabbedTerminalWidget)(new JediTabbedTerminalWidget((TabbedSettingsProvider)(new DefaultTabbedSettingsProvider()),(Function)(new Function() {
      // $FF: synthetic method
      // $FF: bridge method
      public Object apply(Object var1) {
        return this.apply((Pair)var1);
      }
      public final JediTerminalWidget apply(Pair pair) {
        JediTermWidget var10000=JediTerm.this.openSession(pair!=null?(TerminalWidget)pair.getFirst():null);
        if(var10000==null) {
          throw new NullPointerException("null cannot be cast to non-null type com.jediterm.app.JediTerminalWidget");
        }else {
          return (JediTerminalWidget)var10000;
        }
      }
    })) {
      @NotNull
      public JediTerminalWidget createInnerTerminalWidget() {
        JediTerm var10000=JediTerm.this;
        TabbedSettingsProvider var10001=this.getSettingsProvider();
        Intrinsics.checkNotNullExpressionValue(var10001,"settingsProvider");
        return var10000.createTerminalWidget(var10001);
      }
      //  // $FF: synthetic method
      //  // $FF: bridge method
      //  public JediTermWidget createInnerTerminalWidget() {
      //   return (JediTermWidget)this.createInnerTerminalWidget();
      //  }
    });
  }
  //  // $FF: synthetic method
  //  // $FF: bridge method
  //  public AbstractTabbedTerminalWidget createTabbedTerminalWidget() {
  //     return (AbstractTabbedTerminalWidget)this.createTabbedTerminalWidget();
  //  }
  @NotNull
  public TtyConnector createTtyConnector() {
    try {
      Charset charset=StandardCharsets.UTF_8;
      HashMap envs=new HashMap(System.getenv());
      if(Platform.current()==Platform.Mac) {
        ((Map)envs).put("LC_CTYPE",Charsets.UTF_8.name());
      }
      String[] var10000;
      if(UIUtil.isWindows) {
        var10000=new String[] {"powershell.exe"};
      }else {
        ((Map)envs).put("TERM","xterm-256color");
        String var7=(String)envs.get("SHELL");
        if(var7==null) {
          var7="/bin/bash";
        }
        Intrinsics.checkNotNullExpressionValue(var7,"envs[\"SHELL\"] ?: \"/bin/bash\"");
        String shell=var7;
        var10000=UIUtil.isMac?new String[] {shell,"--login"}:new String[] {shell};
      }
      String[] command=var10000;
      AbstractTerminalFrame.LOG.info("Starting "+command+" "+(CharSequence)null+" "+(CharSequence)null+" "+(CharSequence)null+" "+0+" "+(CharSequence)null+" "+(Function1)null+" "+63,(Object)null);
      PtyProcess var8=(new PtyProcessBuilder()).setCommand(command).setEnvironment((Map)envs).setConsole(false).setUseWinConPty(true).start();
      Intrinsics.checkNotNullExpressionValue(var8,"PtyProcessBuilder()\n  …ty(true)\n    .start()");
      PtyProcess process=var8;
      Intrinsics.checkNotNullExpressionValue(charset,"charset");
      return (TtyConnector)(new LoggingPtyProcessTtyConnector(process,charset,ArraysKt.toList(command)));
    }catch(Exception var5) {
      throw new IllegalStateException((Throwable)var5);
    }
  }
  @NotNull
  protected JediTerminalWidget createTerminalWidget(@NotNull TabbedSettingsProvider settingsProvider) {
    Intrinsics.checkNotNullParameter(settingsProvider,"settingsProvider");
    JediTerminalWidget widget=new JediTerminalWidget((SettingsProvider)settingsProvider);
    widget.addHyperlinkFilter((HyperlinkFilter)(new UrlFilter()));
    return widget;
  }
  //  // $FF: synthetic method
  //  // $FF: bridge method
  //  public JediTermWidget createTerminalWidget(TabbedSettingsProvider var1) {
  //     return (JediTermWidget)this.createTerminalWidget(var1);
  //  }
  @Metadata(mv= {1,9,0},k=1,d1= {
    "\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B#\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b¢\u0006\u0002\u0010\nJ\u000e\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00100\bH\u0016J\b\u0010\u0016\u001a\u00020\fH\u0016J\u000e\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00120\bH\u0016J \u0010\u0018\u001a\u00020\f2\u0006\u0010\u0019\u001a\u00020\u00102\u0006\u0010\u001a\u001a\u00020\f2\u0006\u0010\u001b\u001a\u00020\fH\u0016J\u000e\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u0014J\u0010\u0010\u001f\u001a\u00020\u001d2\u0006\u0010 \u001a\u00020!H\u0016J\u0010\u0010\u001f\u001a\u00020\u001d2\u0006\u0010\"\u001a\u00020\tH\u0016R\u000e\u0010\u000b\u001a\u00020\fX\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006#"},d2= {
      "Lcom/jediterm/app/JediTerm$LoggingPtyProcessTtyConnector;","Lcom/jediterm/pty/PtyProcessTtyConnector;","Lcom/jediterm/terminal/LoggingTtyConnector;","process","Lcom/pty4j/PtyProcess;","charset","Ljava/nio/charset/Charset;","command","","","(Lcom/pty4j/PtyProcess;Ljava/nio/charset/Charset;Ljava/util/List;)V","MAX_LOG_SIZE","","logStart","myDataChunks","Ljava/util/LinkedList;","","myStates","Lcom/jediterm/terminal/LoggingTtyConnector$TerminalState;","myWidget","Lcom/jediterm/terminal/ui/JediTermWidget;","getChunks","getLogStart","getStates","read","buf","offset","length","setWidget","","widget","write","bytes","","string","JediTerm"})
  public static final class LoggingPtyProcessTtyConnector extends PtyProcessTtyConnector implements LoggingTtyConnector{
    private final int MAX_LOG_SIZE;
    private final LinkedList myDataChunks;
    private final LinkedList myStates;
    private JediTermWidget myWidget;
    private int logStart;
    public int read(@NotNull char[] buf,int offset,int length) throws IOException {
      Intrinsics.checkNotNullParameter(buf,"buf");
      int len=super.read(buf,offset,length);
      if(len>0) {
        char[] arr=ArraysKt.copyOfRange(buf,offset,len);
        this.myDataChunks.add(arr);
        JediTermWidget var10000=this.myWidget;
        Intrinsics.checkNotNull(var10000);
        TerminalTextBuffer terminalTextBuffer=var10000.getTerminalTextBuffer();
        Intrinsics.checkNotNullExpressionValue(terminalTextBuffer,"terminalTextBuffer");
        String var10002=terminalTextBuffer.getScreenLines();
        String var10003=terminalTextBuffer.getStyleLines();
        LinesBuffer var10004=terminalTextBuffer.getHistoryBuffer();
        Intrinsics.checkNotNullExpressionValue(var10004,"terminalTextBuffer.historyBuffer");
        LoggingTtyConnector.TerminalState terminalState=new LoggingTtyConnector.TerminalState(var10002,var10003,var10004.getLines());
        this.myStates.add(terminalState);
        if(this.myDataChunks.size()>this.MAX_LOG_SIZE) {
          this.myDataChunks.removeFirst();
          this.myStates.removeFirst();
          int var10001=this.logStart++;
        }
      }
      return len;
    }
    @NotNull
    public List getChunks() {
      return (List)(new ArrayList((Collection)this.myDataChunks));
    }
    @NotNull
    public List getStates() {
      return (List)(new ArrayList((Collection)this.myStates));
    }
    public int getLogStart() {
      return this.logStart;
    }
    public void write(@NotNull String string) throws IOException {
      Intrinsics.checkNotNullParameter(string,"string");
      AbstractTerminalFrame.LOG.debug("Writing in OutputStream : "+string);
      super.write(string);
    }
    public void write(@NotNull byte[] bytes) throws IOException {
      Intrinsics.checkNotNullParameter(bytes,"bytes");
      Logger var10000=AbstractTerminalFrame.LOG;
      StringBuilder var10001=(new StringBuilder()).append("Writing in OutputStream : ");
      String var10002=Arrays.toString(bytes);
      Intrinsics.checkNotNullExpressionValue(var10002,"toString(this)");
      var10000.debug(var10001.append(var10002).append(" ").append(new String(bytes,Charsets.UTF_8)).toString());
      super.write(bytes);
    }
    public final void setWidget(@NotNull JediTermWidget widget) {
      Intrinsics.checkNotNullParameter(widget,"widget");
      this.myWidget=widget;
    }
    public LoggingPtyProcessTtyConnector(@NotNull PtyProcess process,@NotNull Charset charset,@NotNull List command) {
      //  Intrinsics.checkNotNullParameter(process, "process");
      //  Intrinsics.checkNotNullParameter(charset, "charset");
      //  Intrinsics.checkNotNullParameter(command, "command");
      super(process,charset,command);
      this.MAX_LOG_SIZE=200;
      this.myDataChunks=new LinkedList();
      this.myStates=new LinkedList();
    }
  }
}
