package org.javadbc.attributes;

/**
 * @author Jvelilla
 * JavaBeans property editory for CheckLevel objects.
 */
import java.beans.PropertyEditorSupport;

import org.springframework.util.StringUtils;

public class CheckLevelEditor extends PropertyEditorSupport {

	public String getAsText() {
		CheckLevel c = (CheckLevel) getValue();
		return c == null ? null : c.getLevel();
	}

	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.hasText(text))
			setValue(CheckLevel.valueOf(text));
	}
	
	public CheckLevelEditor(String level){
		setAsText(level);
	} 

}
