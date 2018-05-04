package rule;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

class Sub_rela_obj {
	private String sub;
	private String rela;
	private String obj;

	Sub_rela_obj() {
		this.rela = "";
		this.obj = "";
		this.obj = "";
	}

	Sub_rela_obj(String sub, String rela, String obj) {
		this.sub = sub;
		this.rela = rela;
		this.obj = obj;
	}

	public String toString() {
		return sub + "  " + rela + "  " + obj;
	}

	public boolean equals(Object other) {
		if (other == this)
			return true;
		if (!(other instanceof Sub_rela_obj))
			return false;

		Sub_rela_obj a = (Sub_rela_obj) other;
		if (this.sub.equals(a.sub) && this.rela.equals(a.rela) && this.obj.equals(a.obj))
			return true;
		else
			return false;
	}

	public int hashCode() {
		return sub.hashCode() ^ rela.hashCode() ^ obj.hashCode();
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public String getRela() {
		return rela;
	}

	public void setRela(String rela) {
		this.rela = rela;
	}

	public String getObj() {
		return obj;
	}

	public void setObj(String obj) {
		this.obj = obj;
	}
}

public class Extract {
	// 从数据集读取规则，构造规则类
	public List<Sub_rela_obj> read() {

		Set<Sub_rela_obj> set = new HashSet<Sub_rela_obj>();

		try {
			FileInputStream fis = null;
			InputStreamReader isr = null;
			BufferedReader br = null;
			String str = "";
			String st1 = "";
			String st2 = "";
			String st3 = "";
			fis = new FileInputStream("./yago2core.10kseedsSample.compressed.notypes.tsv");
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);

			while ((str = br.readLine()) != null) {
				if (str.endsWith("."))
					str = str.replace(">.", ">");
				String[] results = str.split("\\t");
				if (results.length == 3) {
					st1 = results[0];
					st2 = results[1];
					st3 = results[2];
					String _st2 = st2.replace("<", "");
					String _st2_ = _st2.replace(">", "");
					Sub_rela_obj temp = new Sub_rela_obj(st1, _st2_, st3);
					set.add(temp);
				}
			}
			br.close();
			isr.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<Sub_rela_obj> list = new ArrayList<Sub_rela_obj>(set);
		System.out.println(list.size());
		return list;
	}

	// 读取规则及其数量存入Map
	@SuppressWarnings("rawtypes")
	public static HashMap<String, Integer> _read() {

		HashMap<String, Integer> map = new HashMap<String, Integer>();

		try {
			FileInputStream fis = null;
			InputStreamReader isr = null;
			BufferedReader br = null;
			String str = "";
			String st1 = "";
			String st2 = "";
			fis = new FileInputStream("./file/rule@.txt");
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);

			while ((str = br.readLine()) != null) {
				String[] results = str.split("  ");
				if (results.length == 2) {
					st1 = results[0];
					st2 = results[1];
					map.put(st1, Integer.parseInt(st2));
				}
			}
			br.close();
			isr.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Iterator iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
			Object val = entry.getValue();
			System.out.println(key + "	" + val);
		}

		return map;
	}

	@SuppressWarnings("rawtypes")
	public static void write(HashMap<String, Integer> map) {
		Iterator iter = map.entrySet().iterator();
		int i = 134;// 第几条，根据情况改动
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./file/rule.txt", true)));

			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				String rule = (String) entry.getKey();
				int amount = (int) entry.getValue();
				int length = rule.split("\\+").length;
				length += 2;
				i++;
				String _rule = rule.replace("+", "");
				String format = "Rule" + i + ":" + _rule + "		" + length + "		" + amount;

				out.write(format + "\r\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	@SuppressWarnings("rawtypes")
	public static void _write(HashMap<String, Integer> map) {
		Iterator iter = map.entrySet().iterator();
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./file/rule@.txt")));

			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				String rule = (String) entry.getKey();
				int amount = (int) entry.getValue();
				String format = rule + "@" + amount;
				out.write(format + "\r\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	// 长度为2的规则及其数量
	public void two(List<Sub_rela_obj> list) {

		HashMap<String, Integer> map = new HashMap<String, Integer>();

		int length = list.size();
		for (int i = 0; i < length; i++) {
			Sub_rela_obj temp1 = list.get(i);
			String sub1 = temp1.getSub();
			String rela1 = temp1.getRela();
			String obj1 = temp1.getObj();

			for (int j = i + 1; j < length; j++) {
				Sub_rela_obj temp2 = list.get(j);
				String sub2 = temp2.getSub();
				String rela2 = temp2.getRela();
				String obj2 = temp2.getObj();

				String rule = "(?b y:" + rela1 + " ?a) +-> (?a y:" + rela2 + " ?b)";
				String rule1 = "(?b y:" + rela2 + " ?a) +-> (?a y:" + rela1 + " ?b)";
				if (sub1.equals(obj2) && obj1.equals(sub2) && rela1.equals(rela2)) {
					System.out.println(temp1.toString() + "	" + temp2.toString() + "\n");
					if (map.containsKey(rule)) {
						int amount = map.get(rule);
						map.put(rule, amount + 1);
					} else {
						map.put(rule, 1);
					}
				} else if (sub1.equals(obj2) && obj1.equals(sub2) && !rela1.equals(rela2)) {
					System.out.println(temp1.toString() + "	" + temp2.toString() + "\n");
					if (map.containsKey(rule)) {
						int amount = map.get(rule);
						map.put(rule, amount + 1);
						map.put(rule1, amount + 1);
					} else {
						map.put(rule, 1);
						map.put(rule1, 1);
					}
				}
			}
		}
		write(map);
	}

	// 长度为3的规则及其数量
	@SuppressWarnings("rawtypes")
	public void three(List<Sub_rela_obj> list) {

		// HashMap<String, Integer> map = _read();

		HashMap<String, Integer> map = new HashMap<String, Integer>();

		int length = list.size();
		List<List<Sub_rela_obj>> twoList = new ArrayList<List<Sub_rela_obj>>();
		for (int i = 0; i < length; i++) {
			Sub_rela_obj temp1 = list.get(i);
			String sub1 = temp1.getSub();
			// String rela1 = temp1.getRela();
			String obj1 = temp1.getObj();
			for (int j = i + 1; j < length; j++) {
				Sub_rela_obj temp2 = list.get(j);
				String sub2 = temp2.getSub();
				// String rela2 = temp2.getRela();
				String obj2 = temp2.getObj();
				if ((sub1.equals(sub2) && !obj1.equals(obj2)) || (sub1.equals(obj2) && !obj1.equals(sub2))) {
					List<Sub_rela_obj> tempList = new ArrayList<Sub_rela_obj>();
					tempList.add(temp1);
					tempList.add(temp2);
					twoList.add(tempList);
				}

				if ((obj1.equals(sub2) && !sub1.equals(obj2)) || (obj1.equals(obj2) && !sub1.equals(sub2))) {
					List<Sub_rela_obj> tempList = new ArrayList<Sub_rela_obj>();
					tempList.add(temp1);
					tempList.add(temp2);
					twoList.add(tempList);
				}
			}
		}
		System.out.println(twoList.size());

		List<List<Sub_rela_obj>> threeList = new ArrayList<List<Sub_rela_obj>>();
		for (int i = 0; i < 1000; i++) {
			List<Sub_rela_obj> tempList = twoList.get(i);

			Sub_rela_obj temp1 = tempList.get(0);
			String sub1 = temp1.getSub();
			// String rela1 = temp1.getRela();
			String obj1 = temp1.getObj();

			Sub_rela_obj temp2 = tempList.get(1);
			String sub2 = temp2.getSub();
			// String rela2 = temp2.getRela();
			String obj2 = temp2.getObj();

			for (int j = 0; j < length; j++) {
				Sub_rela_obj temp3 = list.get(j);
				String sub3 = temp3.getSub();
				// String rela3 = temp3.getRela();
				String obj3 = temp3.getObj();

				if (sub1.equals(sub2)) {
					if ((obj1.equals(sub3) && obj3.equals(obj2)) || (obj1.equals(obj3) && sub3.equals(obj2))) {

						List<Sub_rela_obj> _tempList = new ArrayList<Sub_rela_obj>();
						_tempList.add(temp1);
						_tempList.add(temp2);
						_tempList.add(temp3);
						threeList.add(_tempList);
					}
				}
				if (sub1.equals(obj2)) {
					if ((obj1.equals(sub3) && obj3.equals(sub2)) || (obj1.equals(obj3) && sub3.equals(sub2))) {

						List<Sub_rela_obj> _tempList = new ArrayList<Sub_rela_obj>();
						_tempList.add(temp1);
						_tempList.add(temp2);
						_tempList.add(temp3);
						threeList.add(_tempList);
					}
				}

				if (obj1.equals(sub2)) {
					if ((sub1.equals(sub3) && obj3.equals(obj2)) || (sub1.equals(obj3) && sub3.equals(obj2))) {

						List<Sub_rela_obj> _tempList = new ArrayList<Sub_rela_obj>();
						_tempList.add(temp1);
						_tempList.add(temp2);
						_tempList.add(temp3);
						threeList.add(_tempList);
					}
				}
				if (obj1.equals(obj2)) {
					if ((sub1.equals(sub3) && obj3.equals(sub2)) || (sub1.equals(obj3) && sub3.equals(sub2))) {

						List<Sub_rela_obj> _tempList = new ArrayList<Sub_rela_obj>();
						_tempList.add(temp1);
						_tempList.add(temp2);
						_tempList.add(temp3);
						threeList.add(_tempList);
					}
				}

			}
		}

		for (int i = 0; i < threeList.size(); i++) {
			List<Sub_rela_obj> tempList = threeList.get(i);
			String rela1 = tempList.get(0).getRela();
			String sub1 = tempList.get(0).getSub();
			String obj1 = tempList.get(0).getObj();

			String rela2 = tempList.get(1).getRela();
			String sub2 = tempList.get(1).getSub();
			String obj2 = tempList.get(1).getObj();

			String rela3 = tempList.get(2).getRela();
			String sub3 = tempList.get(2).getSub();
			// String obj3 = tempList.get(2).getObj();

			String rule = "";
			if (sub1.equals(sub2)) {
				if (obj1.equals(sub3)) {
					rule = "(?a y:" + rela1 + " ?b)" + "(?a y:" + rela2 + " ?c) +-> (?b y:" + rela3 + " ?c)";
				} else {
					rule = "(?a y:" + rela1 + " ?b)" + "(?a y:" + rela2 + " ?c) +-> (?c y:" + rela3 + " ?b)";
				}
			}
			if (sub1.equals(obj2)) {
				if (obj1.equals(sub3)) {
					rule = "(?a y:" + rela1 + " ?b)" + "(?c y:" + rela2 + " ?a) +-> (?b y:" + rela3 + " ?c)";
				} else {
					rule = "(?a y:" + rela1 + " ?b)" + "(?c y:" + rela2 + " ?a) +-> (?c y:" + rela3 + " ?b)";
				}
			}

			if (obj1.equals(sub2)) {
				if (sub1.equals(sub3)) {
					rule = "(?a y:" + rela1 + " ?b)" + "(?b y:" + rela2 + " ?c) +-> (?a y:" + rela3 + " ?c)";
				} else {
					rule = "(?a y:" + rela1 + " ?b)" + "(?b y:" + rela2 + " ?c) +-> (?c y:" + rela3 + " ?a)";
				}
			}
			if (obj1.equals(obj2)) {
				if (sub1.equals(sub3)) {
					rule = "(?a y:" + rela1 + " ?b)" + "(?c y:" + rela2 + " ?b) +-> (?a y:" + rela3 + " ?c)";
				} else {
					rule = "(?a y:" + rela1 + " ?b)" + "(?c y:" + rela2 + " ?b) +-> (?c y:" + rela3 + " ?a)";
				}
			}

			Iterator iter = map.entrySet().iterator();
			int flag = 0;
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				String rule_ = (String) entry.getKey();
				int amount = (int) entry.getValue();
				// String[] a = rule_.split("->");
				// String tempString = a[0];
				if (rule_.equals(rule)) {
					map.put(rule_, amount + 1);
					flag = 1;
					break;
				}
			}
			if (flag == 0) {
				map.put(rule, 1);
			}
		}

		write(map);
	}

	// 长度为4的规则及其数量
	@SuppressWarnings("rawtypes")
	public void four(List<Sub_rela_obj> list) {

		HashMap<String, Integer> map = _read();

		// int length = list.size();
		int length = 10000;
		List<List<Sub_rela_obj>> twoList = new ArrayList<List<Sub_rela_obj>>();
		for (int i = 0; i < length; i++) {
			Sub_rela_obj temp1 = list.get(i);
			String sub1 = temp1.getSub();
			// String rela1 = temp1.getRela();
			String obj1 = temp1.getObj();
			for (int j = i + 1; j < length; j++) {
				Sub_rela_obj temp2 = list.get(j);
				String sub2 = temp2.getSub();
				// String rela2 = temp2.getRela();
				String obj2 = temp2.getObj();
				if ((sub1.equals(sub2) && !obj1.equals(obj2)) || (sub1.equals(obj2) && !obj1.equals(sub2))) {
					List<Sub_rela_obj> tempList = new ArrayList<Sub_rela_obj>();
					tempList.add(temp1);
					tempList.add(temp2);
					twoList.add(tempList);
				}
			}
		}
		System.out.println(twoList.size());

		List<List<Sub_rela_obj>> threeList = new ArrayList<List<Sub_rela_obj>>();
		for (int i = 60000; i < 100000; i++) {
			List<Sub_rela_obj> tempList = twoList.get(i);

			Sub_rela_obj temp1 = tempList.get(0);
			String sub1 = temp1.getSub();
			// String rela1 = temp1.getRela();
			String obj1 = temp1.getObj();

			Sub_rela_obj temp2 = tempList.get(1);
			String sub2 = temp2.getSub();
			// String rela2 = temp2.getRela();
			String obj2 = temp2.getObj();

			for (int j = 0; j < length; j++) {
				Sub_rela_obj temp3 = list.get(j);
				String sub3 = temp3.getSub();
				// String rela3 = temp3.getRela();
				String obj3 = temp3.getObj();

				if (sub1.equals(sub2)) {
					if ((obj2.equals(sub3) && !obj3.equals(obj1) && !obj3.equals(sub2))
							|| (obj2.equals(obj3) && !sub3.equals(obj1) && !sub3.equals(sub2))) {

						List<Sub_rela_obj> _tempList = new ArrayList<Sub_rela_obj>();
						_tempList.add(temp1);
						_tempList.add(temp2);
						_tempList.add(temp3);
						threeList.add(_tempList);
					}
				}
				if (sub1.equals(obj2)) {
					if ((sub2.equals(sub3) && !obj3.equals(obj1) && !obj3.equals(obj2))
							|| (sub2.equals(obj3) && !sub3.equals(obj1) && !sub3.equals(obj2))) {

						List<Sub_rela_obj> _tempList = new ArrayList<Sub_rela_obj>();
						_tempList.add(temp1);
						_tempList.add(temp2);
						_tempList.add(temp3);
						threeList.add(_tempList);
					}
				}
			}
		}

		System.out.println(threeList.size());

		List<List<Sub_rela_obj>> fourList = new ArrayList<List<Sub_rela_obj>>();
		for (int i = 0; i < threeList.size(); i++) {
			List<Sub_rela_obj> tempList = threeList.get(i);

			Sub_rela_obj temp1 = tempList.get(0);
			String sub1 = temp1.getSub();
			// String rela1 = temp1.getRela();
			String obj1 = temp1.getObj();

			Sub_rela_obj temp2 = tempList.get(1);
			String sub2 = temp2.getSub();
			// String rela2 = temp2.getRela();
			String obj2 = temp2.getObj();

			Sub_rela_obj temp3 = tempList.get(2);
			String sub3 = temp3.getSub();
			// String rela3 = temp3.getRela();
			String obj3 = temp3.getObj();

			for (int j = 0; j < length; j++) {
				Sub_rela_obj temp4 = list.get(j);
				String sub4 = temp4.getSub();
				// String rela4 = temp4.getRela();
				String obj4 = temp4.getObj();

				if (sub1.equals(sub2)) {
					if (obj2.equals(sub3)) {
						if ((obj1.equals(sub4) && obj4.equals(obj3)) || (obj1.equals(obj4) && sub4.equals(obj3))) {

							List<Sub_rela_obj> _tempList = new ArrayList<Sub_rela_obj>();
							_tempList.add(temp1);
							_tempList.add(temp2);
							_tempList.add(temp3);
							_tempList.add(temp4);
							fourList.add(_tempList);
						}
					}

					if (obj2.equals(obj3)) {
						if ((obj1.equals(sub4) && obj4.equals(sub3)) || (obj1.equals(obj4) && sub4.equals(sub3))) {

							List<Sub_rela_obj> _tempList = new ArrayList<Sub_rela_obj>();
							_tempList.add(temp1);
							_tempList.add(temp2);
							_tempList.add(temp3);
							_tempList.add(temp4);
							fourList.add(_tempList);
						}
					}
				}

				if (sub1.equals(obj2)) {
					if (sub2.equals(sub3)) {
						if ((obj1.equals(sub4) && obj4.equals(obj3)) || (obj1.equals(obj4) && sub4.equals(obj3))) {

							List<Sub_rela_obj> _tempList = new ArrayList<Sub_rela_obj>();
							_tempList.add(temp1);
							_tempList.add(temp2);
							_tempList.add(temp3);
							_tempList.add(temp4);
							fourList.add(_tempList);
						}
					}

					if (sub2.equals(obj3)) {
						if ((obj1.equals(sub4) && obj4.equals(sub3)) || (obj1.equals(obj4) && sub4.equals(sub3))) {

							List<Sub_rela_obj> _tempList = new ArrayList<Sub_rela_obj>();
							_tempList.add(temp1);
							_tempList.add(temp2);
							_tempList.add(temp3);
							_tempList.add(temp4);
							fourList.add(_tempList);
						}
					}
				}

			}
		}

		System.out.println(fourList.size());

		for (int i = 0; i < fourList.size(); i++) {
			List<Sub_rela_obj> tempList = fourList.get(i);
			String rela1 = tempList.get(0).getRela();
			String sub1 = tempList.get(0).getSub();
			String obj1 = tempList.get(0).getObj();

			String rela2 = tempList.get(1).getRela();
			String sub2 = tempList.get(1).getSub();
			String obj2 = tempList.get(1).getObj();

			String rela3 = tempList.get(2).getRela();
			String sub3 = tempList.get(2).getSub();
			// String obj3 = tempList.get(2).getObj();

			String rela4 = tempList.get(3).getRela();
			String sub4 = tempList.get(3).getSub();
			// String obj4 = tempList.get(3).getObj();

			String rule = "";
			if (sub1.equals(sub2)) {
				if (obj2.equals(sub3)) {
					if (obj1.equals(sub4)) {
						rule = "(?a y:" + rela1 + " ?b)" + "(?a y:" + rela2 + " ?c)" + "(?c y:" + rela3
								+ " ?d) +-> (?b y:" + rela4 + " ?d)";
					} else {
						rule = "(?a y:" + rela1 + " ?b)" + "(?a y:" + rela2 + " ?c)" + "(?c y:" + rela3
								+ " ?d) +-> (?d y:" + rela4 + " ?b)";
					}
				} else {
					if (obj1.equals(sub4)) {
						rule = "(?a y:" + rela1 + " ?b)" + "(?a y:" + rela2 + " ?c)" + "(?d y:" + rela3
								+ " ?c) +-> (?b y:" + rela4 + " ?d)";
					} else {
						rule = "(?a y:" + rela1 + " ?b)" + "(?a y:" + rela2 + " ?c)" + "(?d y:" + rela3
								+ " ?c) +-> (?d y:" + rela4 + " ?b)";
					}
				}
			} else {
				if (sub2.equals(sub3)) {
					if (obj1.equals(sub4)) {
						rule = "(?a y:" + rela1 + " ?b)" + "(?c y:" + rela2 + " ?a)" + "(?c y:" + rela3
								+ " ?d) +-> (?b y:" + rela4 + " ?d)";
					} else {
						rule = "(?a y:" + rela1 + " ?b)" + "(?c y:" + rela2 + " ?a)" + "(?c y:" + rela3
								+ " ?d) +-> (?d y:" + rela4 + " ?b)";
					}
				} else {
					if (obj1.equals(sub4)) {
						rule = "(?a y:" + rela1 + " ?b)" + "(?c y:" + rela2 + " ?a)" + "(?d y:" + rela3
								+ " ?c) +-> (?b y:" + rela4 + " ?d)";
					} else {
						rule = "(?a y:" + rela1 + " ?b)" + "(?c y:" + rela2 + " ?a)" + "(?d y:" + rela3
								+ " ?c) +-> (?d y:" + rela4 + " ?b)";
					}
				}
			}

			Iterator iter = map.entrySet().iterator();
			int flag = 0;
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				String rule_ = (String) entry.getKey();
				int amount = (int) entry.getValue();
				String[] a = rule_.split("->");
				if (a[0].contains(rela1) && a[0].contains(rela2) && a[0].contains(rela3) && a[1].contains(rela4)) {
					map.put(rule_, amount + 1);
					flag = 1;
					break;
				}
			}
			if (flag == 0) {
				map.put(rule, 1);
			}

		}

		_write(map);
	}

	// 长度为5的规则及其数量
	@SuppressWarnings("rawtypes")
	public void five(List<Sub_rela_obj> list) {

		HashMap<String, Integer> map = _read();
		int length = list.size();

		List<List<Sub_rela_obj>> twoList = new ArrayList<List<Sub_rela_obj>>();
		for (int i = 0; i < length; i++) {
			Sub_rela_obj temp1 = list.get(i);
			String sub1 = temp1.getSub();
			// String rela1 = temp1.getRela();
			String obj1 = temp1.getObj();
			for (int j = i + 1; j < length; j++) {
				Sub_rela_obj temp2 = list.get(j);
				String sub2 = temp2.getSub();
				// String rela2 = temp2.getRela();
				String obj2 = temp2.getObj();
				if ((sub1.equals(sub2) && !obj1.equals(obj2)) || (sub1.equals(obj2) && !obj1.equals(sub2))) {
					List<Sub_rela_obj> tempList = new ArrayList<Sub_rela_obj>();
					tempList.add(temp1);
					tempList.add(temp2);
					twoList.add(tempList);
				}
			}
		}
		System.out.println(twoList.size());

		List<List<Sub_rela_obj>> threeList = new ArrayList<List<Sub_rela_obj>>();
		for (int i = 0; i < 1000; i++) {
			List<Sub_rela_obj> tempList = twoList.get(i);

			Sub_rela_obj temp1 = tempList.get(0);
			String sub1 = temp1.getSub();
			// String rela1 = temp1.getRela();
			String obj1 = temp1.getObj();

			Sub_rela_obj temp2 = tempList.get(1);
			String sub2 = temp2.getSub();
			// String rela2 = temp2.getRela();
			String obj2 = temp2.getObj();

			for (int j = 0; j < length; j++) {
				Sub_rela_obj temp3 = list.get(j);
				String sub3 = temp3.getSub();
				// String rela3 = temp3.getRela();
				String obj3 = temp3.getObj();

				if (sub1.equals(sub2)) {
					if ((obj2.equals(sub3) && !obj3.equals(obj1) && !obj3.equals(sub2))
							|| (obj2.equals(obj3) && !sub3.equals(obj1) && !sub3.equals(sub2))) {

						List<Sub_rela_obj> _tempList = new ArrayList<Sub_rela_obj>();
						_tempList.add(temp1);
						_tempList.add(temp2);
						_tempList.add(temp3);
						threeList.add(_tempList);
					}
				}
				if (sub1.equals(obj2)) {
					if ((sub2.equals(sub3) && !obj3.equals(obj1) && !obj3.equals(obj2))
							|| (sub2.equals(obj3) && !sub3.equals(obj1) && !sub3.equals(obj2))) {

						List<Sub_rela_obj> _tempList = new ArrayList<Sub_rela_obj>();
						_tempList.add(temp1);
						_tempList.add(temp2);
						_tempList.add(temp3);
						threeList.add(_tempList);
					}
				}
			}
		}

		System.out.println(threeList.size());

		List<List<Sub_rela_obj>> fourList = new ArrayList<List<Sub_rela_obj>>();
		for (int i = 0; i < threeList.size(); i++) {
			List<Sub_rela_obj> tempList = threeList.get(i);

			Sub_rela_obj temp1 = tempList.get(0);
			String sub1 = temp1.getSub();
			String obj1 = temp1.getObj();

			Sub_rela_obj temp2 = tempList.get(1);
			String sub2 = temp2.getSub();
			String obj2 = temp2.getObj();

			Sub_rela_obj temp3 = tempList.get(2);
			String sub3 = temp3.getSub();
			String obj3 = temp3.getObj();

			for (int j = 0; j < length; j++) {
				Sub_rela_obj temp4 = list.get(j);
				String sub4 = temp4.getSub();
				String obj4 = temp4.getObj();

				if (sub1.equals(sub2)) {
					if (obj2.equals(sub3)) {
						if ((obj3.equals(sub4) && !obj4.equals(sub3) && !obj4.equals(sub2) && !obj4.equals(obj1))
								|| (obj3.equals(obj4) && !sub4.equals(sub3) && !sub4.equals(sub2)
										&& !sub4.equals(obj1))) {

							List<Sub_rela_obj> _tempList = new ArrayList<Sub_rela_obj>();
							_tempList.add(temp1);
							_tempList.add(temp2);
							_tempList.add(temp3);
							_tempList.add(temp4);
							fourList.add(_tempList);
						}
					}

					if (obj2.equals(obj3)) {
						if ((sub3.equals(sub4) && !obj4.equals(obj3) && !obj4.equals(sub2) && !obj4.equals(obj1))
								|| (sub3.equals(obj4) && !sub4.equals(obj3) && !sub4.equals(sub2)
										&& !sub4.equals(obj1))) {

							List<Sub_rela_obj> _tempList = new ArrayList<Sub_rela_obj>();
							_tempList.add(temp1);
							_tempList.add(temp2);
							_tempList.add(temp3);
							_tempList.add(temp4);
							fourList.add(_tempList);
						}
					}
				}

				if (sub1.equals(obj2)) {
					if (sub2.equals(sub3)) {
						if ((obj3.equals(sub4) && !obj4.equals(sub3) && !obj4.equals(obj2) && !obj4.equals(obj1))
								|| (obj3.equals(obj4) && !sub4.equals(sub3) && !sub4.equals(obj2)
										&& !sub4.equals(obj1))) {

							List<Sub_rela_obj> _tempList = new ArrayList<Sub_rela_obj>();
							_tempList.add(temp1);
							_tempList.add(temp2);
							_tempList.add(temp3);
							_tempList.add(temp4);
							fourList.add(_tempList);
						}
					}

					if (sub2.equals(obj3)) {
						if ((sub3.equals(sub4) && !obj4.equals(obj3) && !obj4.equals(obj2) && !obj4.equals(obj1))
								|| (sub3.equals(obj4) && !sub4.equals(obj3) && !sub4.equals(obj2)
										&& !sub4.equals(obj1))) {

							List<Sub_rela_obj> _tempList = new ArrayList<Sub_rela_obj>();
							_tempList.add(temp1);
							_tempList.add(temp2);
							_tempList.add(temp3);
							_tempList.add(temp4);
							fourList.add(_tempList);
						}
					}
				}

			}
		}

		System.out.println(fourList.size());

		List<List<Sub_rela_obj>> fiveList = new ArrayList<List<Sub_rela_obj>>();
		for (int i = 0; i < fourList.size(); i++) {
			List<Sub_rela_obj> tempList = fourList.get(i);

			Sub_rela_obj temp1 = tempList.get(0);
			String sub1 = temp1.getSub();
			String obj1 = temp1.getObj();

			Sub_rela_obj temp2 = tempList.get(1);
			String sub2 = temp2.getSub();
			String obj2 = temp2.getObj();

			Sub_rela_obj temp3 = tempList.get(2);
			String sub3 = temp3.getSub();
			String obj3 = temp3.getObj();

			Sub_rela_obj temp4 = tempList.get(3);
			String sub4 = temp4.getSub();
			String obj4 = temp4.getObj();

			for (int j = 0; j < length; j++) {
				Sub_rela_obj temp5 = list.get(j);
				String sub5 = temp5.getSub();
				String obj5 = temp5.getObj();

				if (sub1.equals(sub2)) {
					if (obj2.equals(sub3)) {
						if (obj3.equals(sub4)) {
							if ((obj1.equals(sub5) && obj5.equals(obj4)) || (obj1.equals(obj5) && sub5.equals(obj4))) {

								List<Sub_rela_obj> _tempList = new ArrayList<Sub_rela_obj>();
								_tempList.add(temp1);
								_tempList.add(temp2);
								_tempList.add(temp3);
								_tempList.add(temp4);
								_tempList.add(temp5);
								fiveList.add(_tempList);
							}
						}
						if (obj3.equals(obj4)) {
							if ((obj1.equals(sub5) && obj5.equals(sub4)) || (obj1.equals(obj5) && sub5.equals(sub4))) {

								List<Sub_rela_obj> _tempList = new ArrayList<Sub_rela_obj>();
								_tempList.add(temp1);
								_tempList.add(temp2);
								_tempList.add(temp3);
								_tempList.add(temp4);
								_tempList.add(temp5);
								fiveList.add(_tempList);
							}
						}
					}

					if (obj2.equals(obj3)) {
						if (sub3.equals(sub4)) {
							if ((obj1.equals(sub5) && obj5.equals(obj4)) || (obj1.equals(obj5) && sub5.equals(obj4))) {

								List<Sub_rela_obj> _tempList = new ArrayList<Sub_rela_obj>();
								_tempList.add(temp1);
								_tempList.add(temp2);
								_tempList.add(temp3);
								_tempList.add(temp4);
								_tempList.add(temp5);
								fiveList.add(_tempList);
							}
						}
						if (sub3.equals(obj4)) {
							if ((obj1.equals(sub5) && obj5.equals(sub4)) || (obj1.equals(obj5) && sub5.equals(sub4))) {

								List<Sub_rela_obj> _tempList = new ArrayList<Sub_rela_obj>();
								_tempList.add(temp1);
								_tempList.add(temp2);
								_tempList.add(temp3);
								_tempList.add(temp4);
								_tempList.add(temp5);
								fiveList.add(_tempList);
							}
						}
					}
				}

				if (sub1.equals(obj2)) {
					if (sub2.equals(sub3)) {
						if (obj3.equals(sub4)) {
							if ((obj1.equals(sub5) && obj5.equals(obj4)) || (obj1.equals(obj5) && sub5.equals(obj4))) {

								List<Sub_rela_obj> _tempList = new ArrayList<Sub_rela_obj>();
								_tempList.add(temp1);
								_tempList.add(temp2);
								_tempList.add(temp3);
								_tempList.add(temp4);
								_tempList.add(temp5);
								fiveList.add(_tempList);
							}
						}
						if (obj3.equals(obj4)) {
							if ((obj1.equals(sub5) && obj5.equals(sub4)) || (obj1.equals(obj5) && sub5.equals(sub4))) {

								List<Sub_rela_obj> _tempList = new ArrayList<Sub_rela_obj>();
								_tempList.add(temp1);
								_tempList.add(temp2);
								_tempList.add(temp3);
								_tempList.add(temp4);
								_tempList.add(temp5);
								fiveList.add(_tempList);
							}
						}
					}

					if (sub2.equals(obj3)) {
						if (sub3.equals(sub4)) {
							if ((obj1.equals(sub5) && obj5.equals(obj4)) || (obj1.equals(obj5) && sub5.equals(obj4))) {

								List<Sub_rela_obj> _tempList = new ArrayList<Sub_rela_obj>();
								_tempList.add(temp1);
								_tempList.add(temp2);
								_tempList.add(temp3);
								_tempList.add(temp4);
								_tempList.add(temp5);
								fiveList.add(_tempList);
							}
						}
						if (sub3.equals(obj4)) {
							if ((obj1.equals(sub5) && obj5.equals(sub4)) || (obj1.equals(obj5) && sub5.equals(sub4))) {

								List<Sub_rela_obj> _tempList = new ArrayList<Sub_rela_obj>();
								_tempList.add(temp1);
								_tempList.add(temp2);
								_tempList.add(temp3);
								_tempList.add(temp4);
								_tempList.add(temp5);
								fiveList.add(_tempList);
							}
						}
					}
				}

			}
		}
		System.out.println(fiveList.size());

		for (int i = 0; i < fiveList.size(); i++) {
			List<Sub_rela_obj> tempList = fiveList.get(i);
			String rela1 = tempList.get(0).getRela();
			String sub1 = tempList.get(0).getSub();
			String obj1 = tempList.get(0).getObj();

			String rela2 = tempList.get(1).getRela();
			String sub2 = tempList.get(1).getSub();
			String obj2 = tempList.get(1).getObj();

			String rela3 = tempList.get(2).getRela();
			String sub3 = tempList.get(2).getSub();
			String obj3 = tempList.get(2).getObj();

			String rela4 = tempList.get(3).getRela();
			String sub4 = tempList.get(3).getSub();
			// String obj4 = tempList.get(3).getObj();

			String rela5 = tempList.get(4).getRela();
			String sub5 = tempList.get(4).getSub();
			// String obj5 = tempList.get(4).getObj();

			String rule = "";
			if (sub1.equals(sub2)) {
				if (obj2.equals(sub3)) {
					if (obj3.equals(sub4)) {
						if (obj1.equals(sub5)) {
							rule = "(?a y:" + rela1 + " ?b)" + "(?a y:" + rela2 + " ?c)" + "(?c y:" + rela3 + " ?d)"
									+ "(?d y:" + rela4 + " ?e) +-> (?b y:" + rela5 + " ?e)";
						} else {
							rule = "(?a y:" + rela1 + " ?b)" + "(?a y:" + rela2 + " ?c)" + "(?c y:" + rela3 + " ?d)"
									+ "(?d y:" + rela4 + " ?e) +-> (?e y:" + rela5 + " ?b)";
						}
					} else {
						if (obj1.equals(sub5)) {
							rule = "(?a y:" + rela1 + " ?b)" + "(?a y:" + rela2 + " ?c)" + "(?c y:" + rela3 + " ?d)"
									+ "(?e y:" + rela4 + " ?d) +-> (?b y:" + rela5 + " ?e)";
						} else {
							rule = "(?a y:" + rela1 + " ?b)" + "(?a y:" + rela2 + " ?c)" + "(?c y:" + rela3 + " ?d)"
									+ "(?e y:" + rela4 + " ?d) +-> (?e y:" + rela5 + " ?b)";
						}
					}
				} else {
					if (sub3.equals(sub4)) {
						if (obj1.equals(sub5)) {
							rule = "(?a y:" + rela1 + " ?b)" + "(?a y:" + rela2 + " ?c)" + "(?d y:" + rela3 + " ?c)"
									+ "(?d y:" + rela4 + " ?e) +-> (?b y:" + rela5 + " ?e)";
						} else {
							rule = "(?a y:" + rela1 + " ?b)" + "(?a y:" + rela2 + " ?c)" + "(?d y:" + rela3 + " ?c)"
									+ "(?d y:" + rela4 + " ?e) +-> (?e y:" + rela5 + " ?b)";
						}
					} else {
						if (obj1.equals(sub5)) {
							rule = "(?a y:" + rela1 + " ?b)" + "(?a y:" + rela2 + " ?c)" + "(?d y:" + rela3 + " ?c)"
									+ "(?e y:" + rela4 + " ?d) +-> (?b y:" + rela5 + " ?e)";
						} else {
							rule = "(?a y:" + rela1 + " ?b)" + "(?a y:" + rela2 + " ?c)" + "(?d y:" + rela3 + " ?c)"
									+ "(?e y:" + rela4 + " ?d) +-> (?e y:" + rela5 + " ?b)";
						}
					}
				}
			} else {
				if (sub2.equals(sub3)) {
					if (obj3.equals(sub4)) {
						if (obj1.equals(sub5)) {
							rule = "(?a y:" + rela1 + " ?b)" + "(?c y:" + rela2 + " ?a)" + "(?c y:" + rela3 + " ?d)"
									+ "(?d y:" + rela4 + " ?e) +-> (?b y:" + rela5 + " ?e)";
						} else {
							rule = "(?a y:" + rela1 + " ?b)" + "(?c y:" + rela2 + " ?a)" + "(?c y:" + rela3 + " ?d)"
									+ "(?d y:" + rela4 + " ?e) +-> (?e y:" + rela5 + " ?b)";
						}
					} else {
						if (obj1.equals(sub5)) {
							rule = "(?a y:" + rela1 + " ?b)" + "(?c y:" + rela2 + " ?a)" + "(?c y:" + rela3 + " ?d)"
									+ "(?e y:" + rela4 + " ?d) +-> (?b y:" + rela5 + " ?e)";
						} else {
							rule = "(?a y:" + rela1 + " ?b)" + "(?c y:" + rela2 + " ?a)" + "(?c y:" + rela3 + " ?d)"
									+ "(?e y:" + rela4 + " ?d) +-> (?e y:" + rela5 + " ?b)";
						}
					}
				} else {
					if (sub3.equals(sub4)) {
						if (obj1.equals(sub5)) {
							rule = "(?a y:" + rela1 + " ?b)" + "(?c y:" + rela2 + " ?a)" + "(?d y:" + rela3 + " ?c)"
									+ "(?d y:" + rela4 + " ?e) +-> (?b y:" + rela5 + " ?e)";
						} else {
							rule = "(?a y:" + rela1 + " ?b)" + "(?c y:" + rela2 + " ?a)" + "(?d y:" + rela3 + " ?c)"
									+ "(?d y:" + rela4 + " ?e) +-> (?e y:" + rela5 + " ?b)";
						}
					} else {
						if (obj1.equals(sub5)) {
							rule = "(?a y:" + rela1 + " ?b)" + "(?c y:" + rela2 + " ?a)" + "(?d y:" + rela3 + " ?c)"
									+ "(?e y:" + rela4 + " ?d) +-> (?b y:" + rela5 + " ?e)";
						} else {
							rule = "(?a y:" + rela1 + " ?b)" + "(?c y:" + rela2 + " ?a)" + "(?d y:" + rela3 + " ?c)"
									+ "(?e y:" + rela4 + " ?d) +-> (?e y:" + rela5 + " ?b)";
						}
					}
				}
			}

			Iterator iter = map.entrySet().iterator();
			int flag = 0;
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				String rule_ = (String) entry.getKey();
				int amount = (int) entry.getValue();
				String[] a = rule_.split("->");
				if (a[0].contains(rela1) && a[1].contains(rela5)) {
					a[0] = a[0].replaceFirst(rela1, "");
					if (a[0].contains(rela2)) {
						a[0] = a[0].replaceFirst(rela2, "");
						if (a[0].contains(rela3)) {
							a[0] = a[0].replaceFirst(rela3, "");
							if (a[0].contains(rela4)) {
								map.put(rule_, amount + 1);
								flag = 1;
								break;
							}
						}
					}
				}
			}
			if (flag == 0) {
				map.put(rule, 1);
			}

		}

		_write(map);

	}

	public static void main(String[] args) {
		// Extract main = new Extract();
		// write(_read());
		// main.two(main.read());
		// main.three(main.read());
		// main.four(main.read());
		// main.five(main.read());
		// main.three2(main.read());
	}
}
