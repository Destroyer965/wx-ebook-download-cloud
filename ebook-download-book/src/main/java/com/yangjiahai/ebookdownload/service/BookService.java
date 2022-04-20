package com.yangjiahai.ebookdownload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yangjiahai.ebookdownload.VO.BookVO;
import com.yangjiahai.ebookdownload.entity.Book;

import java.util.List;
import java.util.Map;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/2/23 20:04
 */
public interface BookService extends IService<Book> {
    /**
     * 根据图书类别id查询所有图书
     * @param pageNo
     * @param pageSize
     * @param categoryId
     * @return
     */
    Map<String, Object> selectBookByCateGoryId(Integer pageNo,Integer pageSize,Long categoryId);

    /**
     * 根据图书id查询图书
     * @param id
     * @return
     */
    Book selectBookById(Long id);

    /**
     * 用户下载图书，图书下载数量增加
     * @param bookid
     * @return
     */
    int downloadBook(Long bookid);

    /**
     * 用户收藏图书，添加图书收藏数量
     * @param bookid
     * @return
     */
    int collectionBook(Long bookid);

    /**
     * 用户取消收藏图书，减少图书收藏数量
     *
     * @param bookid
     * @return
     */
    int delCollectionBook(Long bookid);

    /**
     * 模糊查询图书
     * @param name
     * @return
     */
    List<Book> fuzzyQuery(String name);

    /**
     * 添加搜索记录
     * @param id
     * @param bookid
     * @return
     */
    Double addSearchList(Long id,Long bookid);
    /**
     * 删除搜索记录
     * @param id
     * @return
     */
    boolean delSearchList(Long id);

    /**
     * 根据用户id查询用户搜索历史
     * @param id
     * @return
     */
    List<Book> querySearchList(Long id);

    /**
     * 条件筛选
     * id：图书分类id，name:搜索关键字，sort：排序条件、true为升序没，false为降序
     * name
     * sort
     * @return
     */
    List<Book> selectBookByCondition(Long id,String name,boolean sort);

    /**
     * 查询图书列表
     * @param list
     */
    List<BookVO> selectBookList(List<String> list);

    /**
     * 根据图书id查询图书名
     * @param id
     * @return
     */
    String selectBookNameById(Integer id);
}
