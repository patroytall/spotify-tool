package org.oneplatform.tool.repository.resource;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RepoValue {
  public static class Links {
    public static class Href {
      public String href;
    }
    public static class CloneHref {
      public String href;
      public String name;
    }
    public List<Href> self;  
    public List<CloneHref> clone;       
  }
  public String slug;
  public long id;
  public String name;
  public String scmId;
  public String state;
  public String statusMessage;
  public boolean forkable;
  public ProjectValue project;
  public Links links;

  @JsonProperty("public")
  public boolean publicProp;
}