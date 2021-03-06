package de.uniheidelberg.cl.prog2.node;

import java.util.*;
import java.util.regex.*;


public class NonTerminalNode extends Node {

  static final char OPENPAREN = '(';
  static final char CLOSINGPAREN = ')';

  private ArrayList<Node> children;

  public NonTerminalNode(String label, ArrayList<Node> children) {
    super(label);
    this.children = children;
  }

  ArrayList<Node> getChildren() {
    return this.children;
  }

  public String toString() {

    String result = getLabel() + ":( ";
    for (Node c : this.children) {
      result += c.toString() + " ";
    }
    result += ")";
    return result;
  }


  public static Node parse(StringBuilder nodeRepr) {

    // Subtask (1) 1 Punkt
    // * compile a suitable pattern that recognizes the start of a non-terminal
    //   node
    // * compile a second pattern that recognizes the end of a non-terminal
    //   node
    // * apply both patterns to nodeRepr and return two instances of the Matcher
    //   class called 'opening' and 'closing' respectively
    //

    Pattern emptypattern = Pattern.compile("^\\s*$");
    Pattern startpattern = Pattern.compile("\\s*([^\\s:()]+):\\(");
    Pattern closepattern = Pattern.compile("\\s*\\)");

    Matcher opening = startpattern.matcher(nodeRepr);
    Matcher closing = closepattern.matcher(nodeRepr);


    // Subtask (2) 0.5 Punkte
    // write an if test that uses the regular expressions
    // of the two Matcher objects 'opening' and 'closing'
    // to test if you have a non-terminal

    if (opening.find() && closing.find()) {

      NonTerminalNode nt = new NonTerminalNode(opening.group(1), new ArrayList<Node>());


      // we remove the parentheses that enclosed the non-terminal node
      // we are then going to check inside the non-terminal node string
      // to see if it contains further non-terminal or terminal nodes
      // we delete the closing parentheses at the end first so that indices for
      // the start don't move!
      nodeRepr.delete(closing.end() - 1, closing.end());
      nodeRepr.delete(0, opening.end());

      while (nodeRepr.length() > 0) {

        // Subtask (3) 0.5 Punkte
        // * compile a pattern that matches on a terminal node
        // * apply its matcher method to nodeRepr and assign the resulting
        //   Matcher object to a variable 'term'

        Matcher term = Pattern.compile("\\s*([^\\s:()]+):([^\\s:()]+)\\s*").matcher(nodeRepr);

        // we check if we have terminal in front of us
        if (term.lookingAt()) {

          // Subtask (4) 0.5 Punkte
          // * extract the substring that represents the terminal
          // * assign it to a String variable

          // NB: If you  used the right kind of capturing group in your regular
          // expression above, you could now access the relevant string with
          // the group-method of your Matcher term.

          String terminal = term.group(0);

          // Subtask (5) 0.5 Punkte
          // now that you have isolated the string that
          // represents the terminal,
          // * construct a new terminal node by using the static
          //   parse-method of the TerminalNode class (you pass the
          //   string you extracted above to the parse-method)
          // * assign the new Node instance to a variable called 'tnode'

          StringBuilder sb = new StringBuilder();
          sb.append(terminal);
          Node tnode = TerminalNode.parse(sb);

          // we add the Node tnode that we just generated
          // to the list of children of the  current non-terminal node nt
          nt.getChildren().add(tnode);

          // shorten nodeRepr: we throw away the substring that represents
          // the terminal we just built a node for
          nodeRepr.delete(0, term.end());
          //continue the while loop!
          continue;
        } else {
          // potentially, we can now find a non-terminal

          // Subtask (6) 0.5 Punkte
          // use the Pattern 'emptypattern' (see above) in an if-test
          // to check if nodeRepr contains no further material that could be
          // a node; if that is so, break out of the while loop

          Matcher m = emptypattern.matcher(nodeRepr);
          if (m.matches()) {

            break;
          }

          // because the  non-terminal node that we have in the string  now
          // could be followed by another terminal or non-terminal
          // we have to determine where the upcoming non-terminal ends

          // idea: we go from the beginning of the sequence to the right
          // until we hit a closing parenthesis AND we have seen as many
          // closing parentheses as opening parentheses
          // we store  the index of the last closing parenthesis that is
          // part of the non-terminal
          //
          char c;
          int openings = 0; // count var for opening parentheses we've seen
          int closings = 0; // count var for closing parentheses we've seen


          // Subtask (7) 1 Punkt
          //
          // implement the while loop that iterates over the sequence
          // until you have seen an equal number of closing and opening
          // parentheses; at that point break out of the loop
          //

          int i = 0;
          while (i < nodeRepr.length()) {
            c = nodeRepr.charAt(i);

            i++;

            if (c == OPENPAREN) {
              openings++;
            }
            if (c == CLOSINGPAREN) {
              closings++;
            }
            if (openings != 0 && openings == closings) {
              break;
            }
          }

          // now we extract the string that represents the non-terminal
          // using the substring method

          // NB: make sure you keep the final parenthesis as part of the
          // sequence you give to the new StringBuilder
          StringBuilder newnonterm = new StringBuilder(nodeRepr.substring(0, i));

          // Subtask (8) 0.5 Punkte

          // * now you use recursion to send the sequence we just found
          //   and  stored in the above StringBuilder instance
          //   to the static parse-Method of the  NonTerminalNode class for parsing
          // * assign the Node that you get back to a variable
          //   of type Node called 'ntnode'

          Node ntnode = NonTerminalNode.parse(newnonterm);

          // add Node to children of current non-terminal node nt
          nt.getChildren().add(ntnode);

          //shorten nodeRepr
          nodeRepr.delete(0, i);
          //continue the while loop!
          continue;
        }
      }
      return nt;

    } else {
      // it's not a non-terminal node after all
      return null;
    }
  }

}

// Bei der momentanen Implementierung würde ein Teil des yields nicht berücksichtigt, somit gelöscht und der Rest nicht mehr geparst werden.
// Die Regex-Muster könnten so angepasst werden, dass Klammern und Doppelpunkte mit einem Escape-Zeichen "ausgeschaltet" werden könnten und somit nicht mehr als Sonderzeichen gelten würden.