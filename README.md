glassfish-gost-tls
==================

Прикручиваем КриптоПро JTLS к Glassfish 3.1.2

HOWTO:

1) Задать конфигурацию в файле jsse.properties (только то, что касается keystore и truststore)

2) Собрать jar и положить его в папку lib на сервере.

3) Указать имя класса для listener-a:

        asadmin> set configs.config.config-name.network-config.protocols.protocol.listener-name.ssl.classname=by.iba.web.glassfish.GlassfishGostTLS

Либо то же самое,но в файле domain.xml (конфигурация домена Glassfish). В итоге в файле domain.xml будет что-то такое:

          <protocol security-enabled="true" name="http-listener-2">
            <http default-virtual-server="server" max-connections="250">
              <file-cache></file-cache>
            </http>
            <ssl classname="by.iba.web.glassfish.GlassfishGostTLS" client-auth-enabled="true"></ssl>
          </protocol>

4) Обязательно добавить следующие параметры:

        <jvm-options>-Djavax.net.ssl.keyStore=L:/.keystore</jvm-options>
        <jvm-options>-Djavax.net.ssl.keyStoreType=FloppyStore</jvm-options> // либо HDImageStore, если используется жесткий диск
        <jvm-options>-Djavax.net.ssl.keyStorePassword=123456</jvm-options>
        <jvm-options>-Djavax.net.ssl.trustStore=L:/.keystore</jvm-options>
        <jvm-options>-Djavax.net.ssl.trustStoreType=FloppyStore</jvm-options> // либо HDImageStore, если используется жесткий диск
        <jvm-options>-Djavax.net.ssl.trustStorePassword=123456</jvm-options>
