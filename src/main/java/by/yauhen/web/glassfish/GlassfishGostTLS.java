package by.yauhen.web.glassfish;

import java.net.Socket;
import java.util.Enumeration;

import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSocket;

import com.sun.enterprise.security.ssl.GlassfishSSLSupport;
import com.sun.enterprise.security.ssl.GlassfishServerSocketFactory;
import com.sun.grizzly.util.net.SSLImplementation;
import com.sun.grizzly.util.net.SSLSupport;
import com.sun.grizzly.util.net.ServerSocketFactory;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.CryptoPro.JCP.KeyStore.HDImage.FloppyStore;
import ru.CryptoPro.JCP.KeyStore.HDImage.HDImageStore;

public class GlassfishGostTLS extends SSLImplementation {

    @Override
    public String getImplementationName() {
        return "JCP GostTLS";
    }

    @Override
    public SSLSupport getSSLSupport(Socket sock) {
        return new GlassfishSSLSupport((SSLSocket) sock);
    }

    @Override
    public SSLSupport getSSLSupport(SSLEngine ssle) {
        return new GlassfishSSLSupport(ssle);
    }

    @Override
    public ServerSocketFactory getServerSocketFactory() {
        return new JCPSocketFactory();
    }

    class JCPSocketFactory extends GlassfishServerSocketFactory {

        public static final String CONFIG_FILE = "jsse.properties";
        public static final String PROVIDER = "JCP";
        private static final String TRUSTSTORE = "truststore";
        private static final String KEYSTORE_PASS = "keystorePass";
        private static final String KEYSTORE = "keystore";
        private static final String KEYSTORE_TYPE = "keystoreType";
        private static final String TRUSTSTORE_PASS = "truststorePass";
        private final String[] PROPERTIES;
        private final String HDImageStorePath = "HDImageStore.path";

        public JCPSocketFactory() {
            super();
            this.PROPERTIES = new String[]{"protocol", "protocols", "clientauth", "algorithm",
                "ciphers", "keyAlias", "keypass", KEYSTORE_PASS, KEYSTORE, KEYSTORE_TYPE,
                TRUSTSTORE_PASS, TRUSTSTORE, "sigalg", "keyalg"};

            Properties config = new Properties();
            try {
                config.load(JCPSocketFactory.class.getClassLoader().getResourceAsStream(CONFIG_FILE));
                for (String prop : PROPERTIES) {
                    this.setAttribute(prop, config.get(prop));
                }
            } catch (IOException ex) {
                Logger.getLogger(GlassfishGostTLS.class.getName()).log(Level.SEVERE, null, ex);
            }

            logger.log(Level.INFO, "HDImageStore path: {0}", HDImageStore.getDir());
            logger.log(Level.INFO, "FloppyStore path: {0}", FloppyStore.getDir());

            Enumeration<String> keys = attributes.keys();
            while (keys.hasMoreElements()) {
                logAttr(keys.nextElement());
            }
        }

        private void logAttr(String key) {
            logger.log(Level.INFO, "{0}: {1}", new Object[]{key, attributes.get(key)});
        }
        
    }
}
