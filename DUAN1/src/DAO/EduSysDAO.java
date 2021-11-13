/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.util.List;

/**
 *
 * @author Admin
 */
abstract public class EduSysDAO<E,K> {
     abstract public void insert(E entity);
    abstract public void update(E entity);
    abstract public void delete(K key);
    abstract public List<E> selectAll();
    abstract public E selectByID(K key);
    abstract List<E> selectBySQL(String sql,Object...args);
}
