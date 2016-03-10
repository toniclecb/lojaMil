<h3>Configuracoes necessarias para funcionamento:</h3>
<br />
<br />
<h5>-E-mail:</h5>
<p>
	Para envio de email eh necessario configurar corretamente o arquivo production.properties;
	O envio de e-mails esta sendo mockado, para o e-mail realmente ser enviado eh preciso descomentar o parametro br.com.caelum.vraptor.environment em web.xml.
</p>	
<br />
<h5>-HTTPS:</h5>
<p>
	Configuracao necessaria no Tomcat:
	Connector SSLEnabled="true" acceptCount="100" clientAuth="false" disableUploadTimeout="true" enableLookups="false"
	keystoreFile="c:\certif\ia.p12" keystorePass="1234567" keystoreType="PKCS12" maxSpareThreads="75" maxThreads="150"
	minSpareThreads="25" port="8443" protocol="HTTP/1.1" scheme="https" secure="true" sslProtocol="TLS"/
</p>
<br />
<h5>Chaves</h5>
<p>gerando chaves no openssl: ------->http://blog.didierstevens.com/2015/03/30/howto-make-your-own-cert-with-openssl-on-windows/</p>
criar pasta certif<br />
comandos:<br />
C:\certif>set RANDFILE=c:\certif\.rnd<br />
C:\certif>set OPENSSL_CONF=C:\OpenSSL-Win32\bin\openssl.cfg<br />
C:\certif>c:\OpenSSL-Win32\bin\openssl.exe<br />
OpenSSL> ...<br />
genrsa -out ca.key 4096<br />
req -new -x509 -days 1826 -key ca.key -out ca.crt<br />
genrsa -out ia.key 4096<br />
req -new -key ia.key -out ia.csr    [[[[[nomes digitados tem que ser diferentes]]]]<br />
x509 -req -days 730 -in ia.csr -CA ca.crt -CAkey ca.key -set_serial 01 -out ia.crt<br />
pkcs12 -export -out ia.p12 -inkey ia.key -in ia.crt -chain -CAfile ca.crt<br />
	<br />
<h5>-Banco</h5>
	Alterar os parametros em hibernate.cfg.xml para os desejados.<br />
	Exite um arquivo com o esquma e alguns dados do banco: lojamildb2.sql<br />
	
	