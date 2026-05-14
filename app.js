const friendDetails = {
  "3bbas": {
    title: "Garden night",
    note: "Quiet night-shot energy with a clean black jacket and calm presence.",
    move: "The night garden stance",
  },
  amgad: {
    title: "Mirror sharp",
    note: "Clean fit, phone pose, and the kind of calm that looks intentional.",
    move: "The pocket mirror shot",
  },
  amr: {
    title: "Nile view",
    note: "Brown leather, bright smile, and a balcony photo that already feels like graduation day.",
    move: "The Nile balcony smile",
  },
  ali: {
    title: "Velvet confidence",
    note: "Walks in calm, leaves the frame looking expensive.",
    move: "The crossed-arm entrance",
  },
  bassit: {
    title: "Suit mode",
    note: "Looks like he came prepared for the ceremony and the board meeting.",
    move: "The jacket adjustment",
  },
  fawzi: {
    title: "Night leather",
    note: "City lights, black leather, and a smile that turns the night photo into a memory.",
    move: "The night river smile",
  },
  gemy: {
    title: "Mirror legend",
    note: "Quiet smile, sharp glasses, and a phone that knows all the angles.",
    move: "The clean selfie",
  },
  gohar: {
    title: "Big smile energy",
    note: "Turns any photo into a group memory without trying too hard.",
    move: "The pocket pose",
  },
  hamed: {
    title: "Campus spark",
    note: "The graduation-day grin that says the stress is finally over.",
    move: "The close-up victory",
  },
  honda: {
    title: "Double thumbs",
    note: "Braces, glasses, lanyard, and the official seal of good vibes.",
    move: "The two thumbs up",
  },
  jo: {
    title: "Quiet final boss",
    note: "Low volume, high presence, impossible to ignore.",
    move: "The hands-in-pocket stare",
  },
  karim: {
    title: "Night formal",
    note: "Glasses, suit, bright smile, and ceremony energy after dark.",
    move: "The suit smile",
  },
  kemo: {
    title: "Clean classic",
    note: "White shirt, calm smile, and a photo that already feels framed.",
    move: "The formal chill",
  },
  omar: {
    title: "Soft rebel",
    note: "Wavy hair, clear glasses, and the kind of look that belongs on a poster.",
    move: "The sunlight selfie",
  },
  samir: {
    title: "Warm spark",
    note: "The thumbs-up proof that the whole university arc paid off.",
    move: "The victory pose",
  },
  samy: {
    title: "Night balcony",
    note: "A hoodie, a stare, and the calm after a thousand deadlines.",
    move: "The balcony lean",
  },
  shbeeb: {
    title: "Street smile",
    note: "Shows up casually and somehow the photo gets better.",
    move: "The phone-in-hand walk",
  },
  shreef: {
    title: "Beard frame",
    note: "A strong close-up look turned into a full-body sticker with edge.",
    move: "The serious selfie stare",
  },
  zaid: {
    title: "AI campus smile",
    note: "Bright smile, simple fit, and campus-day energy.",
    move: "The AI sign smile",
  },
  zaki: {
    title: "Bright finish",
    note: "The new burgundy avatar brings the clean final-frame energy.",
    move: "The burgundy pocket pose",
  },
};

const friendNames = [
  "3bbas",
  "amgad",
  "amr",
  "ali",
  "bassit",
  "fawzi",
  "gemy",
  "gohar",
  "hamed",
  "honda",
  "jo",
  "karim",
  "kemo",
  "omar",
  "samir",
  "samy",
  "shbeeb",
  "shreef",
  "zaid",
  "zaki",
];

const friends = friendNames.map((name) => ({
  name,
  photo: `assets/photos/${name}.jpeg`,
  avatar: `assets/avatars/${name}.png`,
  ...friendDetails[name],
}));

const featuredNames = ["samir", "zaki", "shreef", "zaid"];

const awards = [
  "Main character energy",
  "Best laugh in the room",
  "Most likely to save the group",
  "Professor negotiator",
  "Always photo ready",
  "Certified vibe manager",
  "Most dramatic entrance",
  "Group memory keeper",
  "Future CEO behavior",
  "Most likely to arrive late and still win",
  "Best comeback lines",
  "The calm one under pressure",
  "Most likely to make this day legendary",
  "Final-photo specialist",
  "Best fit check",
  "Most likely to start the chant",
  "Group-chat historian",
  "Ceremony scene stealer",
  "Night-photo specialist",
  "Best skyline smile",
];

const stageLines = [
  "The official cast of the day we stopped saying someday and started saying remember when.",
  "Same friends, new chapter, better avatars.",
  "A graduation album with games, inside jokes, and main-character lighting.",
  "One group chat, unlimited stories.",
];

const reelItems = [
  ["Opening scene", "Everyone arrives pretending they are not emotional."],
  ["Photo mode", "One picture becomes twenty because somebody blinked."],
  ["Samir moment", "A warm friend energy that keeps the memory smiling."],
  ["After credits", "Food, stories, promises, and one last selfie."],
];

const vibeRounds = [
  {
    title: "Build the final photo vibe",
    brief: "Pick three friends who can turn the last picture into the one everyone reposts.",
  },
  {
    title: "Save the after-party mood",
    brief: "Pick three friends who can keep the energy alive after the ceremony ends.",
  },
  {
    title: "Make the campus walk iconic",
    brief: "Pick three friends for the slow walk, the laugh, and the final pose.",
  },
  {
    title: "Turn the group chat into a movie",
    brief: "Pick three friends to carry the intro, plot twist, and final scene.",
  },
];

const squadMissions = [
  {
    title: "After-ceremony escape plan",
    brief: "Pick the planner, the hype friend, and the person who somehow knows where everyone parked.",
    roles: ["Planner", "Hype", "Navigator"],
  },
  {
    title: "One perfect group photo",
    brief: "Choose the photographer, the pose director, and the friend who makes everyone laugh at the right second.",
    roles: ["Camera", "Director", "Laugh"],
  },
  {
    title: "Food run before everybody vanishes",
    brief: "Pick the friends who can choose the place, collect the orders, and keep the mood alive.",
    roles: ["Decision", "Orders", "Energy"],
  },
  {
    title: "Last walk across campus",
    brief: "Pick three friends for the slow-motion walk, the quote, and the final look back.",
    roles: ["Walk", "Quote", "Finale"],
  },
  {
    title: "Group-chat rescue mission",
    brief: "Choose the friend with screenshots, the one with context, and the one brave enough to explain.",
    roles: ["Receipts", "Context", "Closer"],
  },
];

const bingoPrompts = [
  "Someone asks for one more photo",
  "A parent becomes the official photographer",
  "Someone disappears for ten minutes",
  "A cap or sash needs fixing",
  "A friend says they are not emotional",
  "Group chat gets flooded",
  "Someone arrives late but smiling",
  "A phone battery panic starts",
  "Someone retakes the same selfie",
  "A professor gets mentioned",
  "Everyone argues about where to eat",
  "Someone says this feels unreal",
  "The best photo is accidental",
  "A jacket gets borrowed",
  "Someone gives a speech out of nowhere",
  "The sun ruins one picture",
  "Someone asks who has the tickets",
  "A friend becomes ceremony manager",
  "One photo has a blink in it",
  "Someone says send me everything",
  "A pose becomes an inside joke",
  "Someone says we need a reel",
  "A random old story returns",
  "Someone forgets where they put something",
  "A proud family moment happens",
  "Someone starts rating outfits",
  "A final goodbye takes too long",
  "Someone says see you tomorrow anyway",
  "A serious picture turns chaotic",
  "Someone becomes the map reader",
];

const mostLikelyPrompts = {
  en: [
    "Most likely to arrive late and still look innocent",
    "Most likely to become the group CEO",
    "Most likely to save the whole plan at the last minute",
    "Most likely to say one more photo",
    "Most likely to turn a normal moment into a story",
    "Most likely to start a chant for no reason",
    "Most likely to know where everyone disappeared",
    "Most likely to make the final reel legendary",
  ],
  ar: [
    "مين أكتر واحد هيتأخر ويبان بريء؟",
    "مين أكتر واحد هيبقى CEO الشلة؟",
    "مين أكتر واحد هينقذ الخطة في آخر لحظة؟",
    "مين أكتر واحد هيقول صورة كمان؟",
    "مين أكتر واحد يحول أي موقف لقصة؟",
    "مين أكتر واحد يبدأ هتاف من غير سبب؟",
    "مين أكتر واحد يعرف الناس اختفت فين؟",
    "مين أكتر واحد هيخلي الريل أسطوري؟",
  ],
};

const whoPrompts = {
  en: [
    "Who would disappear for ten minutes then return with food?",
    "Who would convince everyone that one more photo is necessary?",
    "Who would remember a random first-year story at the worst time?",
    "Who would turn a serious picture into chaos?",
    "Who would become the official ceremony manager?",
    "Who would give a motivational speech out of nowhere?",
    "Who would keep all the screenshots for historical reasons?",
    "Who would make everyone laugh when the photo is supposed to be serious?",
  ],
  ar: [
    "مين ممكن يختفي عشر دقايق ويرجع بأكل؟",
    "مين هيقنع الكل إن لازم صورة كمان؟",
    "مين هيفتكر قصة من أول سنة في أغرب وقت؟",
    "مين يحول الصورة الجدية لفوضى؟",
    "مين هيبقى مدير الحفلة الرسمي؟",
    "مين هيطلع يخطب فجأة؟",
    "مين محتفظ بكل السكرينات للتاريخ؟",
    "مين يضحك الناس في الصورة الجدية؟",
  ],
};

const chaosMissions = {
  en: [
    "Do your graduation pose in five seconds.",
    "Give one sentence to the class of tomorrow.",
    "Choose someone who helped you survive this year.",
    "Recreate your most dramatic university moment.",
    "Give a tiny roast and keep it friendly.",
    "Pick the next person who must spin.",
    "Tell the group what this day will be remembered for.",
    "Make the official final-photo face.",
  ],
  ar: [
    "اعمل بوز التخرج في خمس ثواني.",
    "قول جملة واحدة للدفعة الجاية.",
    "اختار حد ساعدك تعدي السنة دي.",
    "مثل أكتر موقف جامعي درامي ليك.",
    "اعمل roast خفيف ولطيف.",
    "اختار الشخص اللي هيلف بعدك.",
    "قول اليوم ده هيفضل فاكرينه بإيه.",
    "اعمل وش الصورة الرسمية الأخيرة.",
  ],
};

const bracketCategories = {
  en: ["Best Fit Championship", "Main Character Cup", "Best Smile Knockout", "Final Photo MVP"],
  ar: ["بطولة أحسن fit", "كأس الشخصية الرئيسية", "خروج المغلوب لأحسن ضحكة", "نجم الصورة الأخيرة"],
};

const finalAwards = {
  en: [
    "Certified Vibe Manager",
    "Main Character of the Night",
    "Best Fit Finalist",
    "Group Chat Historian",
    "Photo Director",
    "Chaos Coordinator",
    "Ceremony Scene Stealer",
    "Final Selfie Specialist",
    "After-Party Captain",
    "Memory Keeper",
  ],
  ar: [
    "مدير الفايب الرسمي",
    "الشخصية الرئيسية الليلة",
    "نهائي أحسن fit",
    "مؤرخ الجروب شات",
    "مخرج الصور",
    "منسق الفوضى",
    "خاطف مشهد الحفلة",
    "متخصص السيلفي الأخيرة",
    "كابتن ما بعد الحفلة",
    "حارس الذكريات",
  ],
};

const showRounds = {
  en: [
    ["Opening round", "Start with a random spotlight. Everyone gets one loud introduction."],
    ["Most Likely To", "Read the prompt, let the room shout, then reveal a candidate."],
    ["Photo Roulette", "Reveal the photo one step at a time until somebody guesses."],
    ["Wheel of Chaos", "Spin the wheel and give the chosen friend a live mission."],
    ["Final Award", "End the round with one dramatic certificate moment."],
  ],
  ar: [
    ["افتتاح الشو", "ابدأ بسبوت لايت عشوائي وكل واحد ياخد تقديم جامد."],
    ["Most Likely To", "اقرأ السؤال وسيب القاعة تصوت وبعدين اكشف مرشح."],
    ["Photo Roulette", "اكشف الصورة خطوة خطوة لحد ما حد يعرف."],
    ["عجلة المهمات", "لف العجلة وادي المهمة للشخص المختار."],
    ["جائزة النهاية", "اختم الجولة بشهادة درامية."],
  ],
};

const uiCopy = {
  en: {
    sidebarTitle: "Memories & Vibes",
    sidebarEyebrow: "Graduation day",
    stageTitle: "One day, many memories",
    stageEyebrow: "Class friends",
    stageSpotlight: "Spotlight",
    stageConfetti: "Confetti",
    showStart: "Start show",
    showReveal: "Reveal",
    showNext: "Next round",
    voteEmpty: "No votes yet.",
    voteStatus: "Tap friends to collect votes, then reveal the winner.",
    whoStatus: "Pick the friend who fits the scene.",
    photoStatus: "Reveal the photo step by step before guessing.",
    wheelStatus: "Spin, pick a friend, and give them a mission.",
    bracketStatus: "Choose the winner in each face-off until one champion survives.",
    finaleStatus: "Generate shareable ceremony cards for all friends.",
  },
  ar: {
    sidebarTitle: "ذكريات وفايبس",
    sidebarEyebrow: "يوم التخرج",
    stageTitle: "يوم واحد، ذكريات كتير",
    stageEyebrow: "صحاب الدفعة",
    stageSpotlight: "سبوت لايت",
    stageConfetti: "كونفيتي",
    showStart: "ابدأ الشو",
    showReveal: "اكشف",
    showNext: "الجولة اللي بعدها",
    voteEmpty: "لسه مفيش تصويت.",
    voteStatus: "دوس على الأصحاب عشان تجمع أصوات، وبعدين اكشف الفائز.",
    whoStatus: "اختار الشخص الأنسب للموقف.",
    photoStatus: "اكشف الصورة خطوة خطوة قبل التخمين.",
    wheelStatus: "لف العجلة، اختار شخص، واديه مهمة.",
    bracketStatus: "اختار الفائز في كل مواجهة لحد ما يفضل بطل واحد.",
    finaleStatus: "طلع كروت جوائز للشلة كلها.",
  },
};

const tabLabels = {
  en: {
    stage: "Stage",
    show: "Show",
    gallery: "Wall",
    match: "Match",
    vote: "Vote",
    who: "Who?",
    photo: "Photo",
    wheel: "Wheel",
    bracket: "Bracket",
    guess: "Guess",
    moves: "Moves",
    squad: "Mix",
    bingo: "Bingo",
    vibes: "Vibes",
    awards: "Awards",
    finale: "Finale",
  },
  ar: {
    stage: "المسرح",
    show: "الشو",
    gallery: "الصور",
    match: "ماتش",
    vote: "تصويت",
    who: "مين؟",
    photo: "الصورة",
    wheel: "العجلة",
    bracket: "بطولة",
    guess: "خمن",
    moves: "الحركات",
    squad: "سكواد",
    bingo: "بينجو",
    vibes: "فايبس",
    awards: "جوائز",
    finale: "النهاية",
  },
};

let matchFirst = null;
let matchedNames = new Set();
let spotlightIndex = friends.findIndex((friend) => friend.name === "samir");
let currentLanguage = "en";
let showIndex = 0;
let showTimer = 30;
let showInterval = null;
let showRevealed = false;
let voteRound = 1;
let votePrompt = null;
let voteCounts = {};
let whoRound = 1;
let whoPrompt = null;
let whoPicks = [];
let photoCurrent = null;
let photoRevealStep = 0;
let wheelRotation = 0;
let bracketQueue = [];
let bracketWinners = [];
let bracketRoundNumber = 1;
let bracketBattle = 1;
let bracketCategory = null;
let bracketHistory = [];
let vibeRound = 1;
let vibeCurrent = null;
let vibePicks = [];
let guessCurrent = null;
let guessAnswered = false;
let guessScore = 0;
let moveCurrent = null;
let moveAnswered = false;
let moveScore = 0;
let moveRound = 1;
let squadCurrent = null;
let squadRound = 1;
let squadPicks = [];
let bingoCells = [];
let bingoChecked = new Set();
let bingoLocked = false;

const $ = (selector) => document.querySelector(selector);

function titleName(name) {
  return name.charAt(0).toUpperCase() + name.slice(1);
}

function localList(collection) {
  return collection[currentLanguage] || collection.en;
}

function text(key) {
  return (uiCopy[currentLanguage] && uiCopy[currentLanguage][key]) || uiCopy.en[key] || key;
}

function shuffle(items) {
  return [...items].sort(() => Math.random() - 0.5);
}

function pickFriends(count, excludedNames = []) {
  const excluded = new Set(excludedNames);
  return shuffle(friends.filter((friend) => !excluded.has(friend.name))).slice(0, count);
}

function imageWithAvatarFallback(img, friend) {
  img.src = friend.avatar;
  img.onerror = () => {
    img.onerror = null;
    img.src = friend.photo;
  };
}

function imageWithPhotoFallback(img, friend) {
  img.src = friend.photo;
  img.onerror = () => {
    img.onerror = null;
    img.src = friend.avatar;
  };
}

function createFriendThumb(friend, altSuffix = "") {
  const img = document.createElement("img");
  img.alt = `${titleName(friend.name)} ${altSuffix}`.trim();
  imageWithAvatarFallback(img, friend);
  return img;
}

function setLanguage(language) {
  currentLanguage = language;
  document.documentElement.lang = language === "ar" ? "ar" : "en";
  document.documentElement.dir = language === "ar" ? "rtl" : "ltr";
  document.body.dataset.language = language;

  document.querySelector(".sidebar .eyebrow").textContent = text("sidebarEyebrow");
  document.querySelector(".sidebar h1").textContent = text("sidebarTitle");
  document.querySelector("#stage .eyebrow").textContent = text("stageEyebrow");
  document.querySelector(".stage-copy h2").textContent = text("stageTitle");
  $("#spinSpotlight").textContent = text("stageSpotlight");
  $("#confettiButton").textContent = text("stageConfetti");
  $("#startShow").textContent = text("showStart");
  $("#revealShow").textContent = text("showReveal");
  $("#nextShow").textContent = text("showNext");
  $("#voteStatus").textContent = text("voteStatus");
  if (!whoPicks.length) $("#whoFeedback").textContent = text("whoStatus");
  $("#photoStatus").textContent = text("photoStatus");
  $("#wheelStatus").textContent = text("wheelStatus");
  $("#bracketStatus").textContent = text("bracketStatus");
  $("#finaleStatus").textContent = text("finaleStatus");

  document.querySelectorAll(".tab").forEach((tab) => {
    tab.textContent = tabLabels[language][tab.dataset.view] || tab.textContent;
  });
  document.querySelectorAll(".language-button").forEach((button) => {
    button.classList.toggle("active", button.dataset.language === language);
  });

  renderShow();
  renderVote();
  renderWho();
  renderPhotoRoulette();
  renderWheel();
  renderBracket();
  renderFinale();
}

function burstConfetti() {
  const colors = ["#156f64", "#de6d55", "#c49a38", "#315f9b", "#11131b"];

  Array.from({ length: 48 }).forEach((_, index) => {
    const piece = document.createElement("span");
    piece.className = "confetti-piece";
    piece.style.left = `${Math.random() * 100}vw`;
    piece.style.animationDelay = `${Math.random() * 0.38}s`;
    piece.style.background = colors[index % colors.length];
    document.body.append(piece);
    window.setTimeout(() => piece.remove(), 2300);
  });
}

function setMood(mood) {
  document.body.dataset.mood = mood;
  document.querySelectorAll(".mood-button").forEach((button) => {
    button.classList.toggle("active", button.dataset.mood === mood);
  });
}

function setSpotlight(friend) {
  const avatar = $("#spotlightAvatar");
  avatar.alt = `${titleName(friend.name)} spotlight avatar`;
  imageWithAvatarFallback(avatar, friend);
  $("#spotlightName").textContent = friend.name;
  $("#spotlightRole").textContent = friend.title;
}

function spinSpotlight() {
  spotlightIndex = (spotlightIndex + 1 + Math.floor(Math.random() * (friends.length - 1))) % friends.length;
  setSpotlight(friends[spotlightIndex]);
}

function renderStage() {
  $("#stageLine").textContent = shuffle(stageLines)[0];
  $("#friendTotal").textContent = friends.length;
  renderLineup();
  renderMemoryReel();
  setSpotlight(friends[spotlightIndex]);
}

function renderLineup() {
  const lineup = $("#lineupStage");
  lineup.innerHTML = "";

  friends.forEach((friend) => {
    const item = document.createElement("button");
    item.type = "button";
    item.className = `lineup-avatar ${friend.name === "samir" ? "samir-lineup" : ""}`;
    item.title = titleName(friend.name);

    const img = document.createElement("img");
    img.alt = titleName(friend.name);
    imageWithAvatarFallback(img, friend);

    item.append(img);
    item.addEventListener("click", () => {
      spotlightIndex = friends.findIndex((candidate) => candidate.name === friend.name);
      setSpotlight(friend);
    });
    lineup.append(item);
  });
}

function renderMemoryReel() {
  const reel = $("#memoryReel");
  reel.innerHTML = "";

  reelItems.forEach(([label, text]) => {
    const card = document.createElement("article");
    card.className = "reel-card";
    card.innerHTML = `<span>${label}</span><strong>${text}</strong>`;
    reel.append(card);
  });
}

function renderGallery(list = friends) {
  const grid = $("#friendGrid");
  grid.innerHTML = "";

  list.forEach((friend) => {
    const card = document.createElement("article");
    card.className = `friend-card ${featuredNames.includes(friend.name) ? "featured-friend" : ""} ${
      friend.name === "samir" ? "samir-feature" : ""
    }`;

    const badge = document.createElement("span");
    badge.className = "friend-badge";
    badge.textContent = friend.title;

    const img = document.createElement("img");
    img.alt = `${titleName(friend.name)} avatar`;
    imageWithAvatarFallback(img, friend);

    const meta = document.createElement("div");
    meta.className = "friend-meta";
    meta.innerHTML = `<strong>${friend.name}</strong><span>${friend.move}</span>`;

    const note = document.createElement("p");
    note.className = "friend-note";
    note.textContent = friend.note;

    card.append(badge, img, meta, note);
    grid.append(card);
  });
}

function renderMatch() {
  const board = $("#matchBoard");
  board.innerHTML = "";
  matchFirst = null;
  matchedNames = new Set();
  $("#matchScore").textContent = `0 / ${friends.length}`;

  const cards = friends.flatMap((friend) => [
    { type: "photo", friend },
    { type: "name", friend },
  ]);

  shuffle(cards).forEach((cardData) => {
    const card = document.createElement("button");
    card.type = "button";
    card.className = `match-card hidden-card ${cardData.type === "photo" ? "photo-card" : "name-card"}`;
    card.dataset.name = cardData.friend.name;
    card.dataset.type = cardData.type;

    if (cardData.type === "photo") {
      const img = document.createElement("img");
      img.alt = titleName(cardData.friend.name);
      imageWithAvatarFallback(img, cardData.friend);
      card.append(img);
    } else {
      card.textContent = cardData.friend.name;
    }

    card.addEventListener("click", () => flipMatchCard(card));
    board.append(card);
  });
}

function flipMatchCard(card) {
  if (card.classList.contains("matched") || !card.classList.contains("hidden-card")) return;
  card.classList.remove("hidden-card");

  if (!matchFirst) {
    matchFirst = card;
    return;
  }

  const isMatch =
    matchFirst.dataset.name === card.dataset.name &&
    matchFirst.dataset.type !== card.dataset.type;

  if (isMatch) {
    matchFirst.classList.add("matched");
    card.classList.add("matched");
    matchedNames.add(card.dataset.name);
    $("#matchScore").textContent = `${matchedNames.size} / ${friends.length}`;
    if (matchedNames.size === friends.length) burstConfetti();
    matchFirst = null;
    return;
  }

  const previous = matchFirst;
  matchFirst = null;
  window.setTimeout(() => {
    previous.classList.add("hidden-card");
    card.classList.add("hidden-card");
  }, 700);
}

function renderShow() {
  const rounds = localList(showRounds);
  const [title, brief] = rounds[showIndex % rounds.length];
  $("#showKicker").textContent = currentLanguage === "ar" ? "وضع المذيع" : "Host mode";
  $("#showTitle").textContent = title;
  $("#showBrief").textContent = brief;
  $("#showTimer").textContent = showTimer;
  renderShowRundown();

  const result = $("#showResult");
  result.className = "show-result";
  result.innerHTML = "";

  if (!showRevealed) {
    result.innerHTML = `<span>${currentLanguage === "ar" ? "جاهز للكشف" : "Ready to reveal"}</span><strong>${
      currentLanguage === "ar" ? "اضغط اكشف لما القاعة تبقى جاهزة" : "Hit reveal when the room is ready"
    }</strong>`;
    return;
  }

  const friend = shuffle(friends)[0];
  const img = createFriendThumb(friend, "show result");
  const prompt = shuffle(localList(mostLikelyPrompts))[0];
  const mission = shuffle(localList(chaosMissions))[0];
  const award = shuffle(localList(finalAwards))[0];

  result.className = "show-result filled";
  result.innerHTML = `<span>${prompt}</span><strong>${friend.name}</strong><p>${mission}</p><em>${award}</em>`;
  result.prepend(img);
}

function renderShowRundown() {
  const rundown = $("#showRundown");
  rundown.innerHTML = "";
  localList(showRounds).forEach(([title, brief], index) => {
    const card = document.createElement("article");
    card.className = `rundown-card ${index === showIndex ? "active" : ""}`;
    card.innerHTML = `<span>${String(index + 1).padStart(2, "0")}</span><strong>${title}</strong><p>${brief}</p>`;
    rundown.append(card);
  });
}

function startShowTimer() {
  window.clearInterval(showInterval);
  showTimer = 30;
  $("#showTimer").textContent = showTimer;
  showInterval = window.setInterval(() => {
    showTimer -= 1;
    $("#showTimer").textContent = showTimer;
    if (showTimer <= 0) window.clearInterval(showInterval);
  }, 1000);
}

function startShow() {
  showIndex = 0;
  showRevealed = false;
  if (document.documentElement.requestFullscreen) {
    document.documentElement.requestFullscreen().catch(() => {});
  }
  startShowTimer();
  renderShow();
}

function revealShow() {
  showRevealed = true;
  window.clearInterval(showInterval);
  renderShow();
  burstConfetti();
}

function nextShowRound() {
  showIndex = (showIndex + 1) % localList(showRounds).length;
  showRevealed = false;
  startShowTimer();
  renderShow();
}

function renderVote(newPrompt = false) {
  if (!votePrompt || newPrompt) {
    votePrompt = shuffle(localList(mostLikelyPrompts))[0];
    voteCounts = {};
    if (newPrompt) voteRound += 1;
  }

  $("#voteRound").textContent = currentLanguage === "ar" ? `تصويت ${voteRound}` : `Vote ${voteRound}`;
  $("#votePrompt").textContent = votePrompt;
  $("#voteWinner").textContent = text("voteEmpty");
  if (newPrompt || !Object.keys(voteCounts).length) $("#voteStatus").textContent = text("voteStatus");

  const grid = $("#voteGrid");
  grid.innerHTML = "";
  friends.forEach((friend) => {
    const count = voteCounts[friend.name] || 0;
    const button = document.createElement("button");
    button.type = "button";
    button.className = `vote-card ${count ? "has-votes" : ""}`;
    button.innerHTML = `<span>${count} ${currentLanguage === "ar" ? "صوت" : "votes"}</span><strong>${friend.name}</strong><p>${friend.title}</p>`;
    button.prepend(createFriendThumb(friend, "vote option"));
    button.addEventListener("click", () => castVote(friend));
    grid.append(button);
  });
}

function castVote(friend) {
  voteCounts[friend.name] = (voteCounts[friend.name] || 0) + 1;
  $("#voteStatus").textContent =
    currentLanguage === "ar" ? `${titleName(friend.name)} أخد صوت.` : `${titleName(friend.name)} got a vote.`;
  renderVote();
}

function revealVoteWinner() {
  const entries = Object.entries(voteCounts).sort((a, b) => b[1] - a[1]);
  if (!entries.length) {
    $("#voteWinner").textContent = text("voteEmpty");
    return;
  }

  const [name, count] = entries[0];
  $("#voteWinner").textContent =
    currentLanguage === "ar"
      ? `${titleName(name)} كسب بـ ${count} صوت.`
      : `${titleName(name)} wins with ${count} vote${count === 1 ? "" : "s"}.`;
  burstConfetti();
}

function resetVote() {
  voteCounts = {};
  renderVote();
}

function renderWho(newScene = false) {
  if (!whoPrompt || newScene) {
    whoPrompt = shuffle(localList(whoPrompts))[0];
    whoPicks = [];
    if (newScene) whoRound += 1;
  }

  $("#whoRound").textContent = currentLanguage === "ar" ? `مشهد ${whoRound}` : `Scene ${whoRound}`;
  $("#whoPrompt").textContent = whoPrompt;
  $("#whoHint").textContent =
    currentLanguage === "ar" ? "اختاروا بسرعة، مفيش إجابة غلط." : "Pick fast. There is no wrong answer.";
  if (!whoPicks.length) $("#whoFeedback").textContent = text("whoStatus");

  const options = $("#whoOptions");
  options.innerHTML = "";
  friends.forEach((friend) => {
    const picked = whoPicks.includes(friend.name);
    const button = document.createElement("button");
    button.type = "button";
    button.className = `who-option ${picked ? "selected" : ""}`;
    button.disabled = picked;
    button.innerHTML = `<strong>${friend.name}</strong><span>${friend.title}</span>`;
    button.prepend(createFriendThumb(friend, "who option"));
    button.addEventListener("click", () => chooseWho(friend));
    options.append(button);
  });
}

function chooseWho(friend) {
  if (whoPicks.includes(friend.name)) return;
  whoPicks.push(friend.name);
  $("#whoFeedback").textContent =
    currentLanguage === "ar"
      ? `${titleName(friend.name)} اتحط في المشهد.`
      : `${titleName(friend.name)} is locked into the scene.`;
  if (whoPicks.length === 3) burstConfetti();
  renderWho();
}

function renderPhotoRoulette(newPhoto = false) {
  if (!photoCurrent || newPhoto) {
    photoCurrent = shuffle(friends)[0];
    photoRevealStep = 0;
  }

  const frame = $("#photoRouletteFrame");
  const img = $("#photoRouletteImage");
  frame.className = `photo-roulette-frame reveal-step-${photoRevealStep}`;
  imageWithPhotoFallback(img, photoCurrent);
  img.alt = `${titleName(photoCurrent.name)} hidden graduation photo`;
  $("#photoRevealBadge").textContent =
    currentLanguage === "ar" ? `خطوة ${photoRevealStep + 1}` : `Step ${photoRevealStep + 1}`;

  const answer = $("#photoAnswerCard");
  answer.className = "photo-answer-card";
  answer.innerHTML = "";
  if (photoRevealStep < 4) {
    answer.innerHTML = `<span>${currentLanguage === "ar" ? "لسه مستخبي" : "Still hidden"}</span><strong>${
      currentLanguage === "ar" ? "اكشف جزء كمان أو خمن بصوت عالي" : "Reveal more or guess out loud"
    }</strong>`;
    return;
  }

  answer.className = "photo-answer-card revealed";
  answer.innerHTML = `<span>${currentLanguage === "ar" ? "الإجابة" : "Answer"}</span><strong>${photoCurrent.name}</strong><p>${photoCurrent.note}</p>`;
  answer.prepend(createFriendThumb(photoCurrent, "photo answer"));
}

function revealPhotoStep() {
  photoRevealStep = Math.min(photoRevealStep + 1, 4);
  renderPhotoRoulette();
  if (photoRevealStep === 4) burstConfetti();
}

function solvePhotoRoulette() {
  photoRevealStep = 4;
  renderPhotoRoulette();
  burstConfetti();
}

function renderWheel() {
  const wheel = $("#chaosWheel");
  const core = wheel.querySelector(".wheel-core");
  wheel.innerHTML = "";
  wheel.append(core);
  wheel.style.transform = `rotate(${wheelRotation}deg)`;

  shuffle(friends).slice(0, 12).forEach((friend, index) => {
    const label = document.createElement("span");
    label.className = "wheel-name";
    label.style.transform = `rotate(${index * 30}deg) translateY(-128px) rotate(-${index * 30}deg)`;
    label.textContent = friend.name;
    wheel.append(label);
  });

  if (!$("#wheelResult").innerHTML) {
    $("#wheelResult").innerHTML = `<span>${currentLanguage === "ar" ? "مستني اللفة" : "Waiting for spin"}</span><strong>${
      currentLanguage === "ar" ? "اضغط Spin" : "Hit Spin"
    }</strong>`;
  }
}

function spinWheel() {
  const friend = shuffle(friends)[0];
  const mission = shuffle(localList(chaosMissions))[0];
  wheelRotation += 720 + Math.floor(Math.random() * 360);
  $("#chaosWheel").style.transform = `rotate(${wheelRotation}deg)`;
  window.setTimeout(() => {
    const result = $("#wheelResult");
    result.innerHTML = `<span>${currentLanguage === "ar" ? "المهمة" : "Mission"}</span><strong>${friend.name}</strong><p>${mission}</p>`;
    result.prepend(createFriendThumb(friend, "wheel result"));
    burstConfetti();
  }, 780);
}

function startBracket() {
  bracketQueue = shuffle(friends);
  bracketWinners = [];
  bracketRoundNumber = 1;
  bracketBattle = 1;
  bracketHistory = [];
  bracketCategory = shuffle(localList(bracketCategories))[0];
  renderBracket();
}

function normalizeBracketQueue() {
  while (bracketQueue.length < 2 && bracketWinners.length > 0) {
    if (bracketQueue.length === 1) bracketWinners.push(bracketQueue.shift());
    bracketQueue = bracketWinners;
    bracketWinners = [];
    bracketRoundNumber += 1;
    bracketBattle = 1;
  }
}

function renderBracket() {
  if (!bracketQueue.length && !bracketCategory) startBracket();
  normalizeBracketQueue();

  $("#bracketCategory").textContent = bracketCategory || localList(bracketCategories)[0];
  $("#bracketRound").textContent =
    currentLanguage === "ar" ? `الجولة ${bracketRoundNumber}` : `Round ${bracketRoundNumber}`;
  $("#bracketProgress").textContent =
    currentLanguage === "ar"
      ? `${bracketQueue.length + bracketWinners.length} لسه في البطولة`
      : `${bracketQueue.length + bracketWinners.length} still in the tournament`;

  const matchup = $("#bracketMatchup");
  matchup.innerHTML = "";

  if (bracketQueue.length === 1 && bracketWinners.length === 0) {
    const champion = bracketQueue[0];
    const card = document.createElement("article");
    card.className = "champion-card";
    card.innerHTML = `<span>${currentLanguage === "ar" ? "البطل" : "Champion"}</span><strong>${champion.name}</strong><p>${bracketCategory}</p>`;
    card.prepend(createFriendThumb(champion, "bracket champion"));
    matchup.append(card);
    $("#bracketStatus").textContent =
      currentLanguage === "ar" ? `${titleName(champion.name)} كسب البطولة.` : `${titleName(champion.name)} wins the bracket.`;
    burstConfetti();
    renderBracketLog();
    return;
  }

  const pair = bracketQueue.slice(0, 2);
  pair.forEach((friend) => {
    const button = document.createElement("button");
    button.type = "button";
    button.className = "bracket-card";
    button.innerHTML = `<span>${currentLanguage === "ar" ? "اختار" : "Pick winner"}</span><strong>${friend.name}</strong><p>${friend.title}</p>`;
    button.prepend(createFriendThumb(friend, "bracket option"));
    button.addEventListener("click", () => chooseBracketWinner(friend));
    matchup.append(button);
  });

  renderBracketLog();
}

function chooseBracketWinner(friend) {
  const pair = bracketQueue.splice(0, 2);
  const loser = pair.find((candidate) => candidate.name !== friend.name);
  bracketWinners.push(friend);
  bracketHistory.unshift(`${friend.name} > ${loser ? loser.name : "bye"}`);
  bracketBattle += 1;
  renderBracket();
}

function renderBracketLog() {
  const log = $("#bracketLog");
  log.innerHTML = "";
  bracketHistory.slice(0, 8).forEach((line) => {
    const item = document.createElement("span");
    item.textContent = line;
    log.append(item);
  });
}

function renderFinale() {
  const grid = $("#certificateGrid");
  grid.innerHTML = "";
  const awardsList = shuffle(localList(finalAwards));

  friends.forEach((friend, index) => {
    const certificate = document.createElement("article");
    certificate.className = `certificate-card ${featuredNames.includes(friend.name) ? "featured-certificate" : ""}`;
    certificate.innerHTML = `<span>${currentLanguage === "ar" ? "شهادة تخرج الشلة" : "Graduation friends certificate"}</span><strong>${
      friend.name
    }</strong><p>${awardsList[index % awardsList.length]}</p><em>${friend.move}</em>`;
    certificate.prepend(createFriendThumb(friend, "certificate"));
    grid.append(certificate);
  });
}

function renderGuess(newPhoto = false) {
  if (!guessCurrent || newPhoto) {
    guessCurrent = shuffle(friends)[0];
    guessAnswered = false;
  }

  const wrap = $("#guessPhotoWrap");
  const img = $("#guessPhoto");
  wrap.classList.remove("revealed");
  img.alt = `${titleName(guessCurrent.name)} mystery photo`;
  imageWithPhotoFallback(img, guessCurrent);
  $("#guessFeedback").textContent = "Pick the friend hiding behind the blur.";
  $("#guessScore").textContent = guessScore;

  const options = shuffle([guessCurrent, ...pickFriends(3, [guessCurrent.name])]);
  const optionGrid = $("#guessOptions");
  optionGrid.innerHTML = "";

  options.forEach((friend) => {
    const button = document.createElement("button");
    button.type = "button";
    button.textContent = friend.name;
    button.addEventListener("click", () => chooseGuess(friend, button));
    optionGrid.append(button);
  });
}

function chooseGuess(friend, button) {
  if (guessAnswered) return;

  guessAnswered = true;
  $("#guessPhotoWrap").classList.add("revealed");

  const correct = friend.name === guessCurrent.name;
  if (correct) {
    guessScore += 1;
    $("#guessFeedback").textContent = `${titleName(friend.name)} unlocked. Clean eye.`;
    burstConfetti();
  } else {
    $("#guessFeedback").textContent = `Close, but that was ${titleName(guessCurrent.name)}.`;
  }

  $("#guessScore").textContent = guessScore;
  document.querySelectorAll("#guessOptions button").forEach((option) => {
    option.disabled = true;
    if (option.textContent === guessCurrent.name) option.classList.add("correct");
  });
  button.classList.add(correct ? "correct" : "wrong");
}

function renderMove(advance = false) {
  if (advance) moveRound += 1;

  moveCurrent = shuffle(friends)[0];
  moveAnswered = false;
  $("#moveRound").textContent = `Move ${moveRound} - ${moveScore} correct`;
  $("#moveClue").textContent = moveCurrent.move;
  $("#moveHint").textContent = "Whose signature move is this?";
  $("#moveFeedback").textContent = "Guess whose pose or move belongs to the clue.";

  const options = shuffle([moveCurrent, ...pickFriends(3, [moveCurrent.name])]);
  const optionGrid = $("#moveOptions");
  optionGrid.innerHTML = "";

  options.forEach((friend) => {
    const button = document.createElement("button");
    button.type = "button";
    button.className = "move-option";

    const img = document.createElement("img");
    img.alt = titleName(friend.name);
    imageWithAvatarFallback(img, friend);

    const label = document.createElement("div");
    label.innerHTML = `<strong>${friend.name}</strong><span>${friend.title}</span>`;

    button.append(img, label);
    button.addEventListener("click", () => chooseMove(friend, button));
    optionGrid.append(button);
  });
}

function chooseMove(friend, button) {
  if (moveAnswered) return;

  moveAnswered = true;
  const correct = friend.name === moveCurrent.name;

  if (correct) {
    moveScore += 1;
    $("#moveFeedback").textContent = `${titleName(friend.name)} owns that move.`;
    burstConfetti();
  } else {
    $("#moveFeedback").textContent = `That move belongs to ${titleName(moveCurrent.name)}.`;
  }

  $("#moveRound").textContent = `Move ${moveRound} - ${moveScore} correct`;
  document.querySelectorAll("#moveOptions button").forEach((option) => {
    option.disabled = true;
    if (option.querySelector("strong").textContent === moveCurrent.name) option.classList.add("correct");
  });
  button.classList.add(correct ? "correct" : "wrong");
}

function renderVibes(newRound = false) {
  if (!vibeCurrent || newRound) {
    vibeCurrent = shuffle(vibeRounds)[0];
    vibePicks = [];
    if (newRound) vibeRound += 1;
  }

  $("#vibeRound").textContent = `Round ${vibeRound}`;
  $("#vibeTitle").textContent = vibeCurrent.title;
  $("#vibeBrief").textContent = vibeCurrent.brief;
  $("#vibeMeter").style.width = `${(vibePicks.length / 3) * 100}%`;
  renderVibePicks();
  renderVibePool();
}

function renderVibePicks() {
  const picks = $("#vibePicks");
  picks.innerHTML = "";

  Array.from({ length: 3 }).forEach((_, index) => {
    const friend = vibePicks[index];
    const card = document.createElement("article");
    card.className = `vibe-pick ${friend ? "filled" : ""}`;

    if (friend) {
      const img = document.createElement("img");
      img.alt = titleName(friend.name);
      imageWithAvatarFallback(img, friend);
      card.innerHTML = `<span>Pick ${index + 1}</span><strong>${friend.name}</strong>`;
      card.append(img);
    } else {
      card.innerHTML = `<span>Pick ${index + 1}</span><strong>Choose vibe</strong>`;
    }

    picks.append(card);
  });
}

function renderVibePool() {
  const pool = $("#vibePool");
  pool.innerHTML = "";

  friends.forEach((friend) => {
    const selected = vibePicks.some((pick) => pick.name === friend.name);
    const button = document.createElement("button");
    button.type = "button";
    button.className = `vibe-option ${selected ? "selected" : ""} ${friend.name === "samir" ? "samir-option" : ""}`;
    button.disabled = selected || vibePicks.length >= 3;

    const img = document.createElement("img");
    img.alt = titleName(friend.name);
    imageWithAvatarFallback(img, friend);

    const label = document.createElement("div");
    label.innerHTML = `<strong>${friend.name}</strong><span>${friend.title}</span>`;

    button.append(img, label);
    button.addEventListener("click", () => chooseVibeFriend(friend));
    pool.append(button);
  });
}

function chooseVibeFriend(friend) {
  if (vibePicks.length >= 3) return;
  vibePicks.push(friend);

  if (vibePicks.length === 3) {
    const names = vibePicks.map((pick) => titleName(pick.name)).join(", ");
    $("#vibeBrief").textContent = `${names}: vibe locked. This friendship moment is ready.`;
    burstConfetti();
  }

  $("#vibeMeter").style.width = `${(vibePicks.length / 3) * 100}%`;
  renderVibePicks();
  renderVibePool();
}

function renderSquad(newMission = false) {
  if (!squadCurrent || newMission) {
    squadCurrent = shuffle(squadMissions)[0];
    squadPicks = [];
    if (newMission) squadRound += 1;
  }

  $("#squadRound").textContent = `Mission ${squadRound}`;
  $("#squadTitle").textContent = squadCurrent.title;
  $("#squadBrief").textContent = squadCurrent.brief;
  $("#squadFeedback").textContent = "Choose three friends for the mission.";
  renderSquadSlots();
  renderSquadPool();
}

function renderSquadSlots() {
  const slots = $("#squadSlots");
  slots.innerHTML = "";

  squadCurrent.roles.forEach((role, index) => {
    const friend = squadPicks[index];
    const slot = document.createElement("article");
    slot.className = `squad-slot ${friend ? "filled" : ""}`;

    if (friend) {
      const img = document.createElement("img");
      img.alt = titleName(friend.name);
      imageWithAvatarFallback(img, friend);
      slot.innerHTML = `<span>${role}</span><strong>${friend.name}</strong>`;
      slot.append(img);
    } else {
      slot.innerHTML = `<span>${role}</span><strong>Open spot</strong>`;
    }

    slots.append(slot);
  });
}

function renderSquadPool() {
  const pool = $("#squadPool");
  pool.innerHTML = "";

  friends.forEach((friend) => {
    const selected = squadPicks.some((pick) => pick.name === friend.name);
    const button = document.createElement("button");
    button.type = "button";
    button.className = `squad-option ${selected ? "selected" : ""}`;
    button.disabled = selected || squadPicks.length >= 3;

    const img = document.createElement("img");
    img.alt = titleName(friend.name);
    imageWithAvatarFallback(img, friend);

    const label = document.createElement("div");
    label.innerHTML = `<strong>${friend.name}</strong><span>${friend.move}</span>`;

    button.append(img, label);
    button.addEventListener("click", () => chooseSquadFriend(friend));
    pool.append(button);
  });
}

function chooseSquadFriend(friend) {
  if (squadPicks.length >= 3) return;
  squadPicks.push(friend);

  if (squadPicks.length === 3) {
    const names = squadPicks.map((pick) => titleName(pick.name)).join(", ");
    $("#squadFeedback").textContent = `${names}: mission locked.`;
    burstConfetti();
  }

  renderSquadSlots();
  renderSquadPool();
}

function renderBingo(newBoard = false) {
  if (!bingoCells.length || newBoard) {
    const prompts = shuffle(bingoPrompts).slice(0, 24);
    prompts.splice(12, 0, "Free: one group photo");
    bingoCells = prompts;
    bingoChecked = new Set([12]);
    bingoLocked = false;
  }

  renderBingoCells();
}

function renderBingoCells() {
  const board = $("#bingoBoard");
  board.innerHTML = "";

  bingoCells.forEach((prompt, index) => {
    const cell = document.createElement("button");
    cell.type = "button";
    cell.className = `bingo-cell ${bingoChecked.has(index) ? "checked" : ""}`;
    cell.textContent = prompt;
    cell.addEventListener("click", () => toggleBingoCell(index));
    board.append(cell);
  });

  const lines = countBingoLines();
  $("#bingoScore").textContent = lines;
  if (!lines) {
    $("#bingoStatus").textContent = "Tap the moments that already happened.";
  }
}

function toggleBingoCell(index) {
  if (index === 12) return;

  if (bingoChecked.has(index)) {
    bingoChecked.delete(index);
  } else {
    bingoChecked.add(index);
  }

  renderBingoCells();

  const lines = countBingoLines();
  if (lines > 0) {
    $("#bingoStatus").textContent = `${lines} bingo line${lines > 1 ? "s" : ""} locked.`;
    if (!bingoLocked) {
      bingoLocked = true;
      burstConfetti();
    }
  }
}

function countBingoLines() {
  const rows = [0, 5, 10, 15, 20].map((start) => [start, start + 1, start + 2, start + 3, start + 4]);
  const columns = [0, 1, 2, 3, 4].map((start) => [start, start + 5, start + 10, start + 15, start + 20]);
  const diagonals = [
    [0, 6, 12, 18, 24],
    [4, 8, 12, 16, 20],
  ];

  return [...rows, ...columns, ...diagonals].filter((line) => line.every((index) => bingoChecked.has(index))).length;
}

function renderAwards() {
  const grid = $("#awardGrid");
  grid.innerHTML = "";
  const shuffledFriends = shuffle(friends);

  awards.slice(0, friends.length).forEach((award, index) => {
    const friend = shuffledFriends[index];
    const card = document.createElement("article");
    card.className = `award-card ${friend.name === "samir" ? "samir-award" : ""}`;
    card.innerHTML = `<span>${award}</span><strong>${friend.name}</strong><p>${friend.note}</p>`;
    grid.append(card);
  });
}

function activateView(viewId, updateHash = false) {
  const tab = document.querySelector(`.tab[data-view="${viewId}"]`);
  const view = document.getElementById(viewId);
  if (!tab || !view) return;

  document.querySelectorAll(".tab").forEach((item) => item.classList.remove("active"));
  document.querySelectorAll(".view").forEach((item) => item.classList.remove("active"));
  tab.classList.add("active");
  view.classList.add("active");
  if (viewId === "stage") spinSpotlight();
  if (updateHash && window.history && window.location) {
    window.history.replaceState(null, "", `#${viewId}`);
  }
}

function wireTabs() {
  document.querySelectorAll(".tab").forEach((tab) => {
    tab.addEventListener("click", () => activateView(tab.dataset.view, true));
  });
}

$("#spinSpotlight").addEventListener("click", spinSpotlight);
$("#confettiButton").addEventListener("click", burstConfetti);
$("#startShow").addEventListener("click", startShow);
$("#revealShow").addEventListener("click", revealShow);
$("#nextShow").addEventListener("click", nextShowRound);
$("#shuffleGallery").addEventListener("click", () => renderGallery(shuffle(friends)));
$("#resetMatch").addEventListener("click", renderMatch);
$("#newVotePrompt").addEventListener("click", () => renderVote(true));
$("#revealVote").addEventListener("click", revealVoteWinner);
$("#resetVote").addEventListener("click", resetVote);
$("#nextWho").addEventListener("click", () => renderWho(true));
$("#revealPhotoStep").addEventListener("click", revealPhotoStep);
$("#solvePhoto").addEventListener("click", solvePhotoRoulette);
$("#newPhotoRoulette").addEventListener("click", () => renderPhotoRoulette(true));
$("#spinWheel").addEventListener("click", spinWheel);
$("#newBracket").addEventListener("click", startBracket);
$("#nextGuess").addEventListener("click", () => renderGuess(true));
$("#nextMove").addEventListener("click", () => renderMove(true));
$("#newSquad").addEventListener("click", () => renderSquad(true));
$("#newBingo").addEventListener("click", () => renderBingo(true));
$("#newVibe").addEventListener("click", () => renderVibes(true));
$("#rerollAwards").addEventListener("click", renderAwards);
$("#generateFinale").addEventListener("click", renderFinale);
$("#printFinale").addEventListener("click", () => window.print());
document.querySelectorAll(".mood-button").forEach((button) => {
  button.addEventListener("click", () => setMood(button.dataset.mood));
});
document.querySelectorAll(".language-button").forEach((button) => {
  button.addEventListener("click", () => setLanguage(button.dataset.language));
});

wireTabs();
setMood("sunset");
renderStage();
renderShow();
renderGallery();
renderMatch();
renderVote();
renderWho();
renderPhotoRoulette();
renderWheel();
startBracket();
renderGuess();
renderMove();
renderSquad();
renderBingo();
renderVibes();
renderAwards();
renderFinale();
setLanguage("en");
activateView(window.location && window.location.hash ? window.location.hash.slice(1) : "stage");
