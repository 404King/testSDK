package com.example.flutterapp.view;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MessageCodec;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;
import java.util.Map;

public class MyLayoutFactory extends PlatformViewFactory {
    private BinaryMessenger messenger;

    public MyLayoutFactory(BinaryMessenger messenger) {
        super(StandardMessageCodec.INSTANCE);
        this.messenger = messenger;
    }

    @java.lang.Override
    public PlatformView create(android.content.Context context, int i, java.lang.Object o) {
        Map<String, Object> params = (Map<String, Object>) o;
        return new Mylayout(context, messenger, i, params);
    }

    public static void registerWith(PluginRegistry registry) {
        final String key = "MyLayout";
        if (registry.hasPlugin(key)) return;
        PluginRegistry.Registrar registrar = registry.registrarFor(key);
        registrar.platformViewRegistry().registerViewFactory("ImageView", new MyLayoutFactory(registrar.messenger()));
    }
}
