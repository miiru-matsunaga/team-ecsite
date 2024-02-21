package jp.co.internous.dragon.controller;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.internous.dragon.model.domain.MstCategory;
import jp.co.internous.dragon.model.domain.MstProduct;
import jp.co.internous.dragon.model.form.SearchForm;
import jp.co.internous.dragon.model.mapper.MstCategoryMapper;
import jp.co.internous.dragon.model.mapper.MstProductMapper;
import jp.co.internous.dragon.model.session.LoginSession;

/**
 * 商品検索に関する処理を行うコントローラー
 * @author インターノウス
 *
 */
@Controller
@RequestMapping("/dragon")
public class IndexController {

	@Autowired
	private LoginSession loginSession;

	@Autowired
	private MstCategoryMapper categoryMapper;

	@Autowired
	private MstProductMapper productMapper;

	/**
	 * トップページを初期表示する。
	 * @param m 画面表示用オブジェクト
	 * @return トップページ
	 */
	@RequestMapping("/")
	public String index(Model m) {
		m.addAttribute("loginSession",loginSession);
		
		if(!loginSession.isLogined()&&loginSession.getTmpUserId() == 0) {
			//仮ユーザー生成
			Random rand =new Random();
			int tmpUserId = (rand.nextInt(900_000_000)+100_000_000)*-1;
			loginSession.setTmpUserId(tmpUserId);
		}

		List<MstCategory> categories = categoryMapper.find();
		m.addAttribute("categories", categories);

		List<MstProduct> products = productMapper.find();
		m.addAttribute("products", products);
		
		m.addAttribute("selected", 0);

		return "index";
	}

	/**
	 * 検索処理を行う
	 * @param f 検索用フォーム
	 * @param m 画面表示用オブジェクト
	 * @return トップページ
	 */
	@RequestMapping("/searchItem")
	public String searchItem(SearchForm f, Model m) {
		List<MstCategory> categories = categoryMapper.find();
		m.addAttribute("categories", categories);
		m.addAttribute("loginSession",loginSession);
		m.addAttribute("keywords", f.getKeywords());
		m.addAttribute("selected", f.getCategory());

		//全角スペースを半角に変換
		String halfspace = f.getKeywords().replaceAll("　", " ");

		//検索ワード編集
		String editKeywords = halfspace.trim().replaceAll("\\s+", " ");
		String[] searchKeywords = editKeywords.split(" ");

		//情報取得
		List<MstProduct> products = null;
		if (f.getCategory() == 0 && !editKeywords.equals("")) {
			products = productMapper.findByProductName(searchKeywords);
		} else if (f.getCategory() != 0 || !editKeywords.equals("")) {
			products = productMapper.findByCategoryAndProductName( f.getCategory(), searchKeywords);
		} else {
			products = productMapper.find();
		}
		m.addAttribute("products", products);
		return "index";
	}
}
