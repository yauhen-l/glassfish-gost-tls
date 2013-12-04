glassfish-gost-tls
==================

Прикручиваем КриптоПро JTLS к Glassfish 3.1.2

HOWTO:

1) Задать конфигурацию в файле jsse.properties. Пояснения:

        protocol=GostTLS        // не трогаем
        protocols=GostTLS       // не трогаем
        clientauth=true         // true - для двусторонней аутентификации
        algorithm=GostX509      // не трогаем
        ciphers=TLS_CIPHER_2001 // не трогаем

        keyAlias=localhost      // псевдоним ключа/сертификата
        keypass=123456          // пароль к кейстору
        keystorePass=123456     // то же самое, что и предыдущее
        keystore=L:\\.keystore  // пусть к кейстору. Ображаем внимание на слэши для Windows.
        keystoreType=FloppyStore        //либо HDImageStore

        truststorePass=123456   // пароль к траст-стору
        truststore=L:\\.keystore        //пусть к траст-сторe. Также обращаем внимание на слэши.

        sigalg=GOST3411withGOST3410EL //не трогаем, даже не уверен, что атрибут используется
        keyalg=GOST3410                 //не трогаем, даже не уверен, что атрибут используется


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

        client-auth-enabled="true" - это для двусторонней аутентификации

4) Обязательно добавить следующие параметры:

        <jvm-options>-Djavax.net.ssl.keyStore=L:/.keystore</jvm-options>
        <jvm-options>-Djavax.net.ssl.keyStoreType=FloppyStore</jvm-options> // либо HDImageStore, если используется жесткий диск
        <jvm-options>-Djavax.net.ssl.keyStorePassword=123456</jvm-options>
        <jvm-options>-Djavax.net.ssl.trustStore=L:/.keystore</jvm-options>
        <jvm-options>-Djavax.net.ssl.trustStoreType=FloppyStore</jvm-options> // либо HDImageStore, если используется жесткий диск
        <jvm-options>-Djavax.net.ssl.trustStorePassword=123456</jvm-options>


Внимание!!!

Для ОС Windows везде обращаем внимение на слэши в путях к файлам.
