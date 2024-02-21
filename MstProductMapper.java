package jp.co.internous.dragon.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import jp.co.internous.dragon.model.domain.MstProduct;

/**
 * mst_productにアクセスするDAO
 * @author インターノウス
 *
 */
@Mapper
public interface MstProductMapper {
	/**
	 * 商品情報を取得する
	 * @return 商品情報リスト
	 */
	@Select(value="SELECT * FROM mst_product")
	List<MstProduct> find();
	
	/**
	 * キーワード（商品名）を条件に商品情報を取得する
	 * SQLは xml ファイルに記述
	 * @return 商品情報リスト
	 */
	List<MstProduct> findByProductName(@Param("keywords") String[] keywords);
	
	/**
	 * カテゴリとキーワード（商品名）を条件に商品情報を取得する
	 * SQLは xml ファイルに記述
	 * @return 商品情報リスト
	 */
	List<MstProduct> findByCategoryAndProductName(@Param("category") int category, @Param("keywords") String[] keywords);
	
	/**
	 * 商品IDを条件に商品情報を取得する
	 * @param id 商品情報ID
	 * @return 商品情報
	 */
	@Select(value="SELECT * FROM mst_product WHERE id = #{id}")
	MstProduct findById(int id);
	
}
