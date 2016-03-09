<h3>Configuracoes necessarias para funcionamento:</h3>


<h5>-E-mail:</h5>
	<p>Para envio de email eh necessario configurar corretamente o arquivo production.properties;
	O envio de e-mails esta sendo mockado, para o e-mail realmente ser enviado eh preciso descomentar o parametro br.com.caelum.vraptor.environment em web.xml.
	<p>
<h5>-HTTPS:</h5>
	<p>Configuracao necessaria no Tomcat:
	Connector SSLEnabled="true" acceptCount="100" clientAuth="false" disableUploadTimeout="true" enableLookups="false"
	keystoreFile="c:\certif\ia.p12" keystorePass="1234567" keystoreType="PKCS12" maxSpareThreads="75" maxThreads="150"
	minSpareThreads="25" port="8443" protocol="HTTP/1.1" scheme="https" secure="true" sslProtocol="TLS"/
	<p>
<h5>-Banco</h5>
	<p>Alterar os parametros em hibernate.cfg.xml para os desejados.
	<p>
	