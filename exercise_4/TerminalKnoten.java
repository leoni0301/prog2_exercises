public class TerminalKnoten extends Knoten {
  String form;
  String subtype;


  TerminalKnoten(String type, String form, String subtype) {
    super(type);
    this.form = form;
    this.subtype = subtype;

    if (this.type == "affix") {
      switch (this.subtype) {
        case "D":
        case "F":
          break;
        default:
          this.subtype = "";
          break;
      }
    }

  }

  public String getForm() {
    return this.form;
  }

  public String getSubtype() {
    return this.subtype;
  }

  @Override
  public String toString() {
    return  getType() + ":" + getForm() + ":" + getSubtype();
  }
}