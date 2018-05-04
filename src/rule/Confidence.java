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

public class Confidence {

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
			for (int k = 1; k < results.length - 3; k++) {
				results[k] = results[k].replace("?b", "");
				results[k] = results[k].replace("?a", "");
				results[k] = results[k].replace("?c", "");
				results[k] = results[k].replace("?d", "");
				preString += results[k];
			}
			int support = Integer.parseInt(results[results.length - 1]);
			int m = support;
			for (int j = 0; j < list.size(); j++) {
				if (j == i) {
					continue;
				}

				String[] _results = list.get(j).split("  ");
				if (results[results.length - 2].equals(_results[_results.length - 2])) {
					for (int k = 1; k < _results.length - 3; k++) {
						_results[k] = _results[k].replace("?b", "");
						_results[k] = _results[k].replace("?a", "");
						_results[k] = _results[k].replace("?c", "");
						_results[k] = _results[k].replace("?d", "");
					}

					int flag = 0;
					int num = 1;
					while (flag == 0 && num < Integer.parseInt(_results[_results.length - 2])) {
						if (!preString.contains(_results[num])) {
							flag = 1;
						}
						num++;
					}

					if (flag == 0) {
						m += Integer.parseInt(_results[_results.length - 1]);
					}
				}
			}

			float confidence = support / (float) m;
			tempList.add(list.get(i) + "  " + confidence);
		}

		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./file/c.txt")));
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
