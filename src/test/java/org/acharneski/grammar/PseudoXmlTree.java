package org.acharneski.grammar;

public class PseudoXmlTree
{
  public String cat;
  public String dog;
  public PseudoXmlTree()
  {
    super();
  }
  public PseudoXmlTree(String cat, String dog)
  {
    super();
    this.cat = cat;
    this.dog = dog;
  }
  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((cat == null) ? 0 : cat.hashCode());
    result = prime * result + ((dog == null) ? 0 : dog.hashCode());
    return result;
  }
  @Override
  public boolean equals(Object obj)
  {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    PseudoXmlTree other = (PseudoXmlTree) obj;
    if (cat == null)
    {
      if (other.cat != null)
        return false;
    } else if (!cat.equals(other.cat))
      return false;
    if (dog == null)
    {
      if (other.dog != null)
        return false;
    } else if (!dog.equals(other.dog))
      return false;
    return true;
  }
  @Override
  public String toString()
  {
    StringBuilder builder = new StringBuilder();
    builder.append("PseudoXmlTree [cat=");
    builder.append(cat);
    builder.append(", dog=");
    builder.append(dog);
    builder.append("]");
    return builder.toString();
  }
  
  
}
