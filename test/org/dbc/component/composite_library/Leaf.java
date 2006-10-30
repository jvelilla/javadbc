package org.dbc.component.composite_library;

import org.javadbc.attributes.Invariant;

@Invariant("!is_Composite() && parts == null")
public class Leaf extends Component<Leaf>{

	public void operation() {
	   System.out.println("Hacemos algo en el nodo hoja");
	}
}

