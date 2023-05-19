package site.easytobuild.multipurpose.jenkins;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.net.URI;


public class Jenkins {

    private final String projectName;


    private final String host;

    private final String userName;


    private final String token;

    private final String configPath;

    private final String projectType;

    public Jenkins(String projectName, String projectType) {
        this.projectName = projectName;
        this.projectType = projectType;
        userName = "ahmed";
        host = "http://localhost:9090/";
        token = "1186c3bf627a2ca57e12e864481dc83ecc";
        configPath = "@D:\\\\multi-purpose\\\\src\\\\main\\\\java\\\\site\\\\easytobuild\\\\multipurpose\\\\";
    }

    public void createItem() {
        String itemCreationLink = createItemCreationLink();
        String fullConfigPath = getFullConfigPath();
        String cmdGetDocId = "\n" +
                "curl -X POST "+ itemCreationLink + " --user "+ userName + ":" + token + " --data-binary "+ fullConfigPath +" -H \"Content-Type:text/xml\"";

        try {
            Runtime.getRuntime().exec(cmdGetDocId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void buildProject() {
        String buildLink = createBuildLink();
        URI uri = URI.create(buildLink);
        HttpHost host = new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme());
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(new AuthScope(uri.getHost(), uri.getPort()),
				new UsernamePasswordCredentials(userName, token));
        AuthCache authCache = new BasicAuthCache();
        BasicScheme basicAuth = new BasicScheme();
		authCache.put(host, basicAuth);

        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();
		HttpPost httpPost = new HttpPost(uri);
        HttpClientContext localContext = HttpClientContext.create();
		localContext.setAuthCache(authCache);

        try {
            httpClient.execute(host, httpPost, localContext);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String createBuildLink() {
        return host + "job/" + projectName + "/build";
    }

    private String createItemCreationLink() {
        return host + "createItem?name=" + projectName;
    }

    private String getFullConfigPath() {
        return configPath + projectType + ".xml";
    }
}
