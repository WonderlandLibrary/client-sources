package tk.rektsky.Main;

import javafx.application.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.image.*;
import javafx.beans.value.*;
import javafx.util.*;
import javafx.animation.*;
import java.io.*;
import java.util.concurrent.*;
import net.minecraft.client.main.*;
import java.net.*;
import tk.rektsky.Utils.*;

public class Auth extends Application
{
    public static String ign;
    public static String userName;
    public static String role;
    Scene scene;
    StackPane wrapper;
    public static boolean authed;
    public static Stage s;
    
    @Override
    public void start(final Stage stage) throws Exception {
        (Auth.s = stage).setTitle("RektSky");
        final Label label = new Label();
        label.setText("RektSky");
        label.setStyle("-fx-font-size: 40px; -fx-text-fill: white; -fx-font-family: monospace;");
        final Label version = new Label();
        version.setText("Login System");
        version.setStyle("-fx-font-size: 25px; -fx-text-fill: white; -fx-font-family: monospace;");
        final Label changelog = new Label();
        changelog.setText("Press 'Launch' to verify your HWID\n with the server.");
        changelog.setStyle("-fx-font-size: 15px; -fx-text-fill: white;");
        changelog.setAlignment(Pos.CENTER_LEFT);
        final Button button = new Button();
        button.setText("Launch");
        button.setMinWidth(270.0);
        button.setMinHeight(50.0);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                Auth.this.startClient(stage);
            }
        });
        button.setStyle("-fx-font-size: 30px; -fx-text-fill: black; -fx-background-color: white;");
        final VBox bottom = new VBox();
        bottom.setAlignment(Pos.BOTTOM_CENTER);
        bottom.getChildren().addAll(button);
        final VBox top = new VBox();
        top.setAlignment(Pos.TOP_CENTER);
        top.getChildren().addAll(label, version, changelog);
        final StackPane window = new StackPane();
        window.getChildren().addAll(top, bottom);
        window.setStyle("-fx-background-color: black");
        this.wrapper = new StackPane();
        this.wrapper.getChildren().addAll(window);
        (this.scene = new Scene(this.wrapper, 250.0, 400.0)).setFill(Color.BLACK);
        stage.setResizable(false);
        stage.setScene(this.scene);
        stage.setAlwaysOnTop(true);
        stage.setResizable(true);
        stage.getIcons().add(new Image("assets/minecraft/rektsky/icons/icon.png"));
        stage.show();
    }
    
    private void startClient(final Stage stage) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: dup            
        //     4: invokespecial   javafx/scene/control/Label.<init>:()V
        //     7: astore_2        /* lbl1 */
        //     8: aload_2         /* lbl1 */
        //     9: ldc             "Obtaining HWID..."
        //    11: invokevirtual   javafx/scene/control/Label.setText:(Ljava/lang/String;)V
        //    14: aload_2         /* lbl1 */
        //    15: ldc             "-fx-font-size: 20px; -fx-text-fill: black;"
        //    17: invokevirtual   javafx/scene/control/Label.setStyle:(Ljava/lang/String;)V
        //    20: new             Ljavafx/scene/control/Label;
        //    23: dup            
        //    24: invokespecial   javafx/scene/control/Label.<init>:()V
        //    27: astore_3        /* lbl2 */
        //    28: aload_3         /* lbl2 */
        //    29: ldc             "Contacting Server..."
        //    31: invokevirtual   javafx/scene/control/Label.setText:(Ljava/lang/String;)V
        //    34: aload_3         /* lbl2 */
        //    35: ldc             "-fx-font-size: 20px; -fx-text-fill: black;"
        //    37: invokevirtual   javafx/scene/control/Label.setStyle:(Ljava/lang/String;)V
        //    40: new             Ljavafx/scene/control/Label;
        //    43: dup            
        //    44: invokespecial   javafx/scene/control/Label.<init>:()V
        //    47: astore          lbl3
        //    49: aload           lbl3
        //    51: ldc             "Failed to authenticate"
        //    53: invokevirtual   javafx/scene/control/Label.setText:(Ljava/lang/String;)V
        //    56: aload           lbl3
        //    58: ldc             "-fx-font-size: 20px; -fx-text-fill: white;"
        //    60: invokevirtual   javafx/scene/control/Label.setStyle:(Ljava/lang/String;)V
        //    63: new             Ljavafx/scene/control/Label;
        //    66: dup            
        //    67: invokespecial   javafx/scene/control/Label.<init>:()V
        //    70: astore          lbl4
        //    72: aload           lbl4
        //    74: ldc             "Launching Client..."
        //    76: invokevirtual   javafx/scene/control/Label.setText:(Ljava/lang/String;)V
        //    79: aload           lbl4
        //    81: ldc             "-fx-font-size: 20px; -fx-text-fill: black;"
        //    83: invokevirtual   javafx/scene/control/Label.setStyle:(Ljava/lang/String;)V
        //    86: new             Ljavafx/scene/layout/VBox;
        //    89: dup            
        //    90: invokespecial   javafx/scene/layout/VBox.<init>:()V
        //    93: astore          verticalAlign
        //    95: aload           verticalAlign
        //    97: invokevirtual   javafx/scene/layout/VBox.getChildren:()Ljavafx/collections/ObservableList;
        //   100: iconst_3       
        //   101: anewarray       Ljavafx/scene/Node;
        //   104: dup            
        //   105: iconst_0       
        //   106: aload_2         /* lbl1 */
        //   107: aastore        
        //   108: dup            
        //   109: iconst_1       
        //   110: aload_3         /* lbl2 */
        //   111: aastore        
        //   112: dup            
        //   113: iconst_2       
        //   114: aload           lbl3
        //   116: aastore        
        //   117: invokeinterface javafx/collections/ObservableList.addAll:([Ljava/lang/Object;)Z
        //   122: pop            
        //   123: aload           verticalAlign
        //   125: getstatic       javafx/geometry/Pos.CENTER:Ljavafx/geometry/Pos;
        //   128: invokevirtual   javafx/scene/layout/VBox.setAlignment:(Ljavafx/geometry/Pos;)V
        //   131: aload           verticalAlign
        //   133: ldc2_w          210.0
        //   136: invokevirtual   javafx/scene/layout/VBox.setSpacing:(D)V
        //   139: aload           verticalAlign
        //   141: ldc2_w          220.0
        //   144: invokevirtual   javafx/scene/layout/VBox.setTranslateY:(D)V
        //   147: aload           verticalAlign
        //   149: ldc             "-fx-background-color: linear-gradient(to bottom, #f0f8ff, #000000);"
        //   151: invokevirtual   javafx/scene/layout/VBox.setStyle:(Ljava/lang/String;)V
        //   154: new             Ljavafx/scene/layout/StackPane;
        //   157: dup            
        //   158: invokespecial   javafx/scene/layout/StackPane.<init>:()V
        //   161: astore          loadingWindow
        //   163: aload           loadingWindow
        //   165: invokevirtual   javafx/scene/layout/StackPane.getChildren:()Ljavafx/collections/ObservableList;
        //   168: aload           verticalAlign
        //   170: invokeinterface javafx/collections/ObservableList.add:(Ljava/lang/Object;)Z
        //   175: pop            
        //   176: aload           loadingWindow
        //   178: aload_0         /* this */
        //   179: getfield        tk/rektsky/Main/Auth.scene:Ljavafx/scene/Scene;
        //   182: invokevirtual   javafx/scene/Scene.getHeight:()D
        //   185: invokevirtual   javafx/scene/layout/StackPane.setTranslateY:(D)V
        //   188: new             Ljavafx/animation/Timeline;
        //   191: dup            
        //   192: invokespecial   javafx/animation/Timeline.<init>:()V
        //   195: astore          timeline
        //   197: new             Ljavafx/animation/KeyValue;
        //   200: dup            
        //   201: aload           loadingWindow
        //   203: invokevirtual   javafx/scene/layout/StackPane.translateYProperty:()Ljavafx/beans/property/DoubleProperty;
        //   206: iconst_0       
        //   207: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   210: getstatic       javafx/animation/Interpolator.EASE_BOTH:Ljavafx/animation/Interpolator;
        //   213: invokespecial   javafx/animation/KeyValue.<init>:(Ljavafx/beans/value/WritableValue;Ljava/lang/Object;Ljavafx/animation/Interpolator;)V
        //   216: astore          kv
        //   218: new             Ljavafx/animation/KeyFrame;
        //   221: dup            
        //   222: ldc2_w          0.5
        //   225: invokestatic    javafx/util/Duration.seconds:(D)Ljavafx/util/Duration;
        //   228: iconst_1       
        //   229: anewarray       Ljavafx/animation/KeyValue;
        //   232: dup            
        //   233: iconst_0       
        //   234: aload           kv
        //   236: aastore        
        //   237: invokespecial   javafx/animation/KeyFrame.<init>:(Ljavafx/util/Duration;[Ljavafx/animation/KeyValue;)V
        //   240: astore          kf
        //   242: aload           timeline
        //   244: invokevirtual   javafx/animation/Timeline.getKeyFrames:()Ljavafx/collections/ObservableList;
        //   247: aload           kf
        //   249: invokeinterface javafx/collections/ObservableList.add:(Ljava/lang/Object;)Z
        //   254: pop            
        //   255: aload           timeline
        //   257: aload           verticalAlign
        //   259: aload           timeline
        //   261: aload           loadingWindow
        //   263: aload           lbl3
        //   265: aload_1         /* stage */
        //   266: invokedynamic   BootstrapMethod #0, handle:(Ljavafx/scene/layout/VBox;Ljavafx/animation/Timeline;Ljavafx/scene/layout/StackPane;Ljavafx/scene/control/Label;Ljavafx/stage/Stage;)Ljavafx/event/EventHandler;
        //   271: invokevirtual   javafx/animation/Timeline.setOnFinished:(Ljavafx/event/EventHandler;)V
        //   274: aload           timeline
        //   276: invokevirtual   javafx/animation/Timeline.play:()V
        //   279: aload_0         /* this */
        //   280: getfield        tk/rektsky/Main/Auth.wrapper:Ljavafx/scene/layout/StackPane;
        //   283: invokevirtual   javafx/scene/layout/StackPane.getChildren:()Ljavafx/collections/ObservableList;
        //   286: aload           loadingWindow
        //   288: invokeinterface javafx/collections/ObservableList.add:(Ljava/lang/Object;)Z
        //   293: pop            
        //   294: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Could not infer any expression.
        //     at com.strobel.decompiler.ast.TypeAnalysis.runInference(TypeAnalysis.java:374)
        //     at com.strobel.decompiler.ast.TypeAnalysis.run(TypeAnalysis.java:96)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:344)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    static {
        Auth.ign = "Reeker" + RektSkyUtil.genRandomString(10);
        Auth.userName = "unknown";
        Auth.role = "member";
        Auth.authed = false;
    }
}
