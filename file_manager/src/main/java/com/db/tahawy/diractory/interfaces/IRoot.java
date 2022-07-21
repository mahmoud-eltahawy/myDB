package com.db.tahawy.diractory.interfaces;

import java.util.List;

public interface IRoot extends IDir{

	List<IFile> getUncles();

	void setUncles();

}
