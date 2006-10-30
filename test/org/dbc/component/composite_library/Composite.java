package org.dbc.component.composite_library;


public class Composite<E> extends Component<E> {

	public boolean isComposite() {
		return true;
	}

    public void operation() {
    
    	for (Component<E> elem : parts) {
		     elem.operation();
		}
    }	
}


