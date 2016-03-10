<h3>Configuracoes necessarias para funcionamento:</h3>


<h5>-E-mail:</h5>
	Para envio de email eh necessario configurar corretamente o arquivo production.properties;
	O envio de e-mails esta sendo mockado, para o e-mail realmente ser enviado eh preciso descomentar o parametro br.com.caelum.vraptor.environment em web.xml.
	
<h5>-HTTPS:</h5>
	Configuracao necessaria no Tomcat:
	Connector SSLEnabled="true" acceptCount="100" clientAuth="false" disableUploadTimeout="true" enableLookups="false"
	keystoreFile="c:\certif\ia.p12" keystorePass="1234567" keystoreType="PKCS12" maxSpareThreads="75" maxThreads="150"
	minSpareThreads="25" port="8443" protocol="HTTP/1.1" scheme="https" secure="true" sslProtocol="TLS"/
	
<h5>Chaves</h5>
gerando chaves no openssl: ------->http://blog.didierstevens.com/2015/03/30/howto-make-your-own-cert-with-openssl-on-windows/
criar pasta certif
comandos:
C:\certif>set RANDFILE=c:\certif\.rnd
C:\certif>set OPENSSL_CONF=C:\OpenSSL-Win32\bin\openssl.cfg
C:\certif>c:\OpenSSL-Win32\bin\openssl.exe
OpenSSL> ...
genrsa -out ca.key 4096
req -new -x509 -days 1826 -key ca.key -out ca.crt
genrsa -out ia.key 4096
req -new -key ia.key -out ia.csr    [[[[[nomes digitados tem que ser diferentes]]]]
x509 -req -days 730 -in ia.csr -CA ca.crt -CAkey ca.key -set_serial 01 -out ia.crt
pkcs12 -export -out ia.p12 -inkey ia.key -in ia.crt -chain -CAfile ca.crt
	
<h5>-Banco</h5>
	Alterar os parametros em hibernate.cfg.xml para os desejados.
	
	