package rule;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class Cross_degree {

	static List<String> list = new ArrayList<String>();
	static List<String> tempList = new ArrayList<String>();

	public static void main(String[] args) {
		try {
			FileInputStream fis = null;
			InputStreamReader isr = null;
			BufferedReader br = null;
			String str = "";
			fis = new FileInputStream("./file/rule.txt");
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);

			while ((str = br.readLine()) != null) {
				list.add(str);
			}
			br.close();
			isr.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < list.size(); i++) {
			String[] results = list.get(i).split("  ");
			String preString = "";
			for (int k = 1; k < results.length - 4; k++) {
				results[k] = results[k].replace("?b", "");
				results[k] = results[k].replace("?a", "");
				results[k] = results[k].replace("?c", "");
				results[k] = results[k].replace("?d", "");
				preString += results[k];
			}
			System.out.println(preString);
			int out = 1;
			int in = 1;
			results[results.length - 4] = results[results.length - 4].replace("?b", "");
			results[results.length - 4] = results[results.length - 4].replace("?a", "");
			results[results.length - 4] = results[results.length - 4].replace("?c", "");
			results[results.length - 4] = results[results.length - 4].replace("?d", "");
			String head = results[results.length - 4];

			for (int j = 0; j < list.size(); j++) {
				if (j == i) {
					continue;
				}

				String[] _results = list.get(j).split("  ");
				if (results[results.length - 3].equals(_results[_results.length - 3])) {
					for (int k = 1; k < _results.length - 3; k++) {
						_results[k] = _results[k].replace("?b", "");
						_results[k] = _results[k].replace("?a", "");
						_results[k] = _results[k].replace("?c", "");
						_results[k] = _results[k].replace("?d", "");
					}

					String _head = _results[_results.length - 4];
					if (head.equals(_head)) {
						in += 1;
					}

					int flag = 0;
					int num = 1;
					String _preString = preString;
					while (flag == 0 && num < Integer.parseInt(_results[_results.length - 3])) {
						if (!_preString.contains(_results[num])) {
							flag = 1;
						} else {
							_preString = _preString.replace(_results[num], "");
							num++;
						}
					}

					if (flag == 0) {
						out += 1;
					}
				}
			}
			int q = in + out - 1;
			float cross_degree = 1 / (float) q;
			tempList.add(list.get(i) + "  " + cross_degree);
		}

		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./file/c_d.txt")));
			for (int i = 0; i < tempList.size(); i++) {
				out.write(tempList.get(i) + "\r\n");
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
}
