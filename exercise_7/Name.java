import java.util.ArrayList;
import java.util.Collections;


public class Name implements Comparable<Name> {

  private String firstName;
  private String lastName;
  private String middleName;

  public Name(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public Name(String firstName, String middleName, String lastName) {
    this(firstName, lastName);
    this.middleName = middleName;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getMiddleName() {
    return this.middleName;
  }

  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  @Override
  public String toString() {
    if (middleName == null) {
      return this.firstName + " " + this.lastName;
    } else {
      return this.firstName + " " + this.middleName + " " + this.lastName;
    }
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.firstName == null) ? 0 : this.firstName.hashCode());
    result = prime * result + ((this.lastName == null) ? 0 : this.lastName.hashCode());
    result = prime * result + ((this.middleName == null) ? 0 : this.middleName.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Name other = (Name) obj;
    if (this.firstName == null) {
      if (other.firstName != null)
        return false;
    } else if (!this.firstName.equals(other.firstName))
      return false;
    if (this.lastName == null) {
      if (other.lastName != null)
        return false;
    } else if (!this.lastName.equals(other.lastName))
      return false;
    if (this.middleName == null) {
      if (other.middleName != null)
        return false;
    } else if (!this.middleName.equals(other.middleName))
      return false;
    return true;
  }

  @Override
  public int compareTo(Name other) {
    String thisLast = this.lastName;
    String otherLast = other.getLastName();
    int lastComp = thisLast.compareTo(otherLast);
    if (lastComp != 0) {
      return lastComp;
    }
    String thisFirst;
    String otherFirst;
    if (this.middleName != null) {
      thisFirst = this.firstName + this.middleName;
    } else {
      thisFirst = this.firstName;
    }
    if (other.getMiddleName() != null) {
      otherFirst = other.getFirstName() + other.getMiddleName();
    } else {
      otherFirst = other.getFirstName();
    }
    return thisFirst.compareTo(otherFirst);
  }


}
