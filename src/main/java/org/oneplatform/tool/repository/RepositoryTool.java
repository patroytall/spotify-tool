package org.oneplatform.tool.repository;

import org.oneplatform.commons.test.http.HttpClient;
import org.oneplatform.commons.test.rest.RestClient;
import org.oneplatform.tool.repository.resource.Project;
import org.oneplatform.tool.repository.resource.ProjectValue;
import org.oneplatform.tool.repository.resource.Repo;
import org.oneplatform.tool.repository.resource.RepoValue;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RepositoryTool {
  private static final String BITBUCKET_HOST = "bitbucket.oneplatform.org";
  private static final String BASE_URL = "https://" + BITBUCKET_HOST + "/rest/api/1.0/";
  
  private static final String PATH_PROJECTS = "projects/";
  private static final String PATH_REPOS = "repos/";

  private static final String PROPERTY_USERNAME = "username";
  private static final String PROPERTY_PASSWORD = "password";
  private static final String PROPERTY_USEPROXY = "useproxy";

  private final RestClient restClient;
  
  private RepositoryTool(RestClient restClient) {
    this.restClient = restClient;
  }
  
  private static Properties getResourceProperties() {
    Properties properties = new Properties();
    InputStream in = RepositoryTool.class.getResourceAsStream("/repository-tool.properties");
    try {
      properties.load(in);
      in.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return properties;
  }

  public static void main(String[] args) {
    Properties properties = getResourceProperties();
    
    boolean useProxy = Boolean.valueOf((String)properties.get(PROPERTY_USEPROXY));
    
    try (HttpClient httpClient = new HttpClient(useProxy)) {
      httpClient.setCredentials(BITBUCKET_HOST, properties.getProperty(PROPERTY_USERNAME),
          properties.getProperty(PROPERTY_PASSWORD));
      
      try (RestClient restClient = new RestClient(httpClient, BASE_URL)) {
        new RepositoryTool(restClient).run();
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void run() {
    Project projects = restClient.get(PATH_PROJECTS, Project.class);
    for (ProjectValue projectValue : projects.values) {
      String projectKey = projectValue.key;
      Repo repo = restClient.get(PATH_PROJECTS + projectKey + "/" + PATH_REPOS, Repo.class);
      for (RepoValue repoValue : repo.values) {
        System.out.println(repoValue.slug);  
      }
    }
  }
  
//  private void clone(String repository) {
//    Runtime run = Runtime.getRuntime();
//    Process p;
//    try {
//      p = run.exec(getBashPath() + " -c 'git clone'");
//      p.waitFor();
//    } catch (IOException | InterruptedException e) {
//      throw new RuntimeException(e);
//    }
//  }
//  
//  private String getBashPath() {
//    String path = "c:/install/cygwin/bin/bash.exe";
//    if (!new File(path).exists()) {
//      throw new RuntimeException("no bash at: " + path);
//    }
//    return path;
//  }
}