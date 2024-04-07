package com.sky.service;

import com.mybatisflex.core.service.IService;
import com.sky.entity.AddressBook;
import java.util.List;

public interface AddressBookService extends IService<AddressBook> {

    List<AddressBook> list(AddressBook addressBook);

    void saveAdd(AddressBook addressBook);

    AddressBook getById(Long id);

    void update(AddressBook addressBook);

    void setDefault(AddressBook addressBook);

    void deleteById(Long id);

}
