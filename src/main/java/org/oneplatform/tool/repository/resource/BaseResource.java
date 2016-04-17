package org.oneplatform.tool.repository.resource;

import java.util.List;

public class BaseResource<T> {
  public boolean isLastPage;
  public long limit;
  public long size;
  public long start;

  public List<T> values;
}