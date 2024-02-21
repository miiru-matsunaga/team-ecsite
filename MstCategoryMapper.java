package jp.co.internous.dragon.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import jp.co.internous.dragon.model.domain.MstCategory;;

/**
 * mst_categoryにアクセスするDAO
 * @author インターノウス
 *
 */
@Mapper
public interface MstCategoryMapper {
	
	/**
	 * カテゴリー情報を取得する
	 * @return カテゴリー情報リスト
	 */
	@Select(value="SELECT * FROM mst_category")
	List<MstCategory> find();

}
