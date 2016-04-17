package org.oneplatform.tool.repository.resource;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ProjectValue {
  public static class Links {
    public static class Href {
      public String href;
    }
    public List<Href> self;  
  }
  
  public String description;
  public long id;
  public String key;
  public Links links;
  public String name;

  @JsonProperty("public")
  public boolean publicProp;

  public String type;
}
