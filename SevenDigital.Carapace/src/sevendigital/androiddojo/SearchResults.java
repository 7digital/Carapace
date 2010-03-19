package sevendigital.androiddojo;

import java.util.List;

public class SearchResults {
	private List<String> _results;
	private int _totalCount;
	private int _page;

	public SearchResults(List<String> results, int totalCount, int page) {
		this._results = results;
		_totalCount = totalCount;
		_page = page;
	}

	public List<String> get_results() {
		return _results;
	}

	public int get_totalCount() {
		return _totalCount;
	}

	public int get_page() {
		return _page;
	}
}
